package com.kr1sel.dto;

import com.kr1sel.models.AppUser;

public record MeetupDTO(String eventName,
                        String location,
                        boolean isPrivate,
                        int maxPeople,
                        String authorUsername) {
}
