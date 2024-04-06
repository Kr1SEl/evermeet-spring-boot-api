package com.kr1sel.dto;

import com.kr1sel.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

public record MeetupDTO(String eventName,
                        String location,
                        boolean isPrivate,
                        int maxPeople,
                        UserDetails author) {
}
