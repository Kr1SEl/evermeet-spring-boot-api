package com.kr1sel.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class AbstractModel {
    @Id
    @GeneratedValue
    private Long id;

}
