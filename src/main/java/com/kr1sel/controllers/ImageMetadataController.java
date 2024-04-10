package com.kr1sel.controllers;

import com.kr1sel.exceptions.NotSufficientPrivilegesException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.ImageMetadata;
import com.kr1sel.services.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/image")
public class ImageMetadataController {

    private final ImageMetadataService imageMetadataService;

    @Autowired
    public ImageMetadataController(ImageMetadataService imageMetadataService){
        this.imageMetadataService = imageMetadataService;
    }

    @GetMapping
    public List<ImageMetadata> getAllImages(@AuthenticationPrincipal AppUser user)
            throws NotSufficientPrivilegesException {
        return imageMetadataService.findAllImages(user);
    }

    @GetMapping("{id}")
    public ImageMetadata getImageByUserId(@PathVariable Long id)
            throws UserNotFoundException {
        return imageMetadataService.findImageByUserId(id);
    }

    @PostMapping
    public void uploadUserImage(@RequestParam("image") MultipartFile image,
                            @RequestParam(value = "caption", required = false, defaultValue = "User avatar") String caption,
                            @AuthenticationPrincipal AppUser user)
            throws IOException {
        imageMetadataService.uploadImageWithCaption(image, caption, user);
    }

}
