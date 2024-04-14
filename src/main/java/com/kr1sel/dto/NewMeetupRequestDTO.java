package com.kr1sel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr1sel.utils.Interest;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

public record NewMeetupRequestDTO(
        @NotNull
        @NotBlank
        @Size(min=3, max=50,
                message = "Event name should be between 3 and 50 characters")
        String eventName,

        String location,

        @NotNull
        boolean isPrivate,

        @NotNull
        @Min(value = 2,
                message = "You can create meetup for at least two people")
        @Max(value = 100,
                message = "Large meetups should receive approval from local government")
        int maxPeople,

        @NotNull
        @Size(min = 1)
        Set<Interest> interests,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDateTime
) {
}
