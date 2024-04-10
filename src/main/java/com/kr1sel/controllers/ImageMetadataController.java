package com.kr1sel.controllers;

import com.kr1sel.models.ImageMetadata;
import com.kr1sel.services.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ImageMetadata> getAllImages(){
        return imageMetadataService.findAllImages();
    }

    @PostMapping
    public void uploadImage(@RequestParam("image") MultipartFile image,
                            @RequestParam("caption") String caption)
            throws IOException {
        imageMetadataService.uploadImageWithCaption(image, caption);
    }

}
