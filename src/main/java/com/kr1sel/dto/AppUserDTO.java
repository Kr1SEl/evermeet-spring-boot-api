package com.kr1sel.dto;


import com.kr1sel.utils.Interest;

import java.util.Set;

public record AppUserDTO(String username,
                         String name,
                         short age,
                         String location,
                         Set<Interest> interests) {
}
