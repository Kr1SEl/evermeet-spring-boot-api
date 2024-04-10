package com.kr1sel.repositories;

import com.kr1sel.models.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
}
