package com.kr1sel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr1sel.utils.Interest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public record NewAppUserRequestDTO(

        @Valid

        @NotNull
        @NotBlank
        @Size(min=3, max=50,
                message = "Name should be between 3 and 50 characters")
        String name,

        @NotNull
        @NotBlank
        @Size(min=3, max=16,
                message = "Username should be between 3 and 16 characters")
        String username,

        @NotNull
        @NotBlank
        @Size(min=8, max=25,
                message = "Password should be between 8 and 25 characters")
        String password,

        @NotNull
        @Min(value = 18,
                message = "Your age should be over 18 to participate in meetups")
        short age,

        @NotNull
        @NotBlank
        @Size(min=3, max=255,
                message = "Location name should be between 3 and 255 characters")
        String location,

        @NotNull
        @NotBlank
        @Size(min = 1)
        Set<Interest> interests

) {
}
