package com.kr1sel.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ImageMetadata extends AbstractModel{

    private String caption;

    private String imageUrl;

    public ImageMetadata(String caption, String imageUrl) {
        this.caption = caption;
        this.imageUrl = imageUrl;
    }
}
