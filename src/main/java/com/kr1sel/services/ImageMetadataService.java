package com.kr1sel.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.kr1sel.exceptions.NotSufficientPrivilegesException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.ImageMetadata;
import com.kr1sel.repositories.ImageMetadataRepository;
import com.kr1sel.utils.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageMetadataService {

    private final ImageMetadataRepository imageMetadataRepository;
    private final AppUserService appUserService;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string}")
    private String connectionString;

    @Autowired
    public ImageMetadataService(ImageMetadataRepository imageMetadataRepository,
                                AppUserService appUserService){
        this.imageMetadataRepository = imageMetadataRepository;
        this.appUserService = appUserService;
    }

    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void init(){
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public List<ImageMetadata> findAllImages(AppUser user) throws NotSufficientPrivilegesException {
        if(user.isAdminUser()){
            return imageMetadataRepository.findAll();
        }else{
            throw new NotSufficientPrivilegesException();
        }
    }

    public ImageMetadata findImageByUserId(Long id)
            throws UserNotFoundException {
        Optional<ImageMetadata> userImage =  imageMetadataRepository.findById(id);
        if(userImage.isPresent()){
            return userImage.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    public void uploadImageWithCaption(MultipartFile image, String caption, AppUser user)
            throws IOException {
        String blobFilename = user.getId().toString() + '#' + user.getUsername() +
                '.' + FileUtil.getFileExtension(image);
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFilename);
        blobClient.upload(image.getInputStream(), image.getSize(), true);

        String imageUrl = blobClient.getBlobUrl();

        ImageMetadata imageMetadata = new ImageMetadata(caption, imageUrl);
        imageMetadataRepository.save(imageMetadata);
        appUserService.updateUserImage(imageMetadata, user);
    }
}
