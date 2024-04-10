package com.kr1sel.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.kr1sel.models.ImageMetadata;
import com.kr1sel.repositories.ImageMetadataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageMetadataService {

    private final ImageMetadataRepository imageMetadataRepository;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string}")
    private String connectionString;

    @Autowired
    public ImageMetadataService(ImageMetadataRepository imageMetadataRepository){
        this.imageMetadataRepository = imageMetadataRepository;
    }

    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void init(){
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public List<ImageMetadata> findAllImages(){
        return imageMetadataRepository.findAll();
    }

    public void uploadImageWithCaption(MultipartFile image, String caption) throws IOException {
        String blobFilename = image.getOriginalFilename();
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFilename);
        blobClient.upload(image.getInputStream(), image.getSize(), true);

        String imageUrl = blobClient.getBlobUrl();

        ImageMetadata imageMetadata = new ImageMetadata(caption, imageUrl);
        imageMetadataRepository.save(imageMetadata);
    }
}
