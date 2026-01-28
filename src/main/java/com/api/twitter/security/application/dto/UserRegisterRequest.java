package com.api.twitter.security.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegisterRequest (
        @NotEmpty(message = "Invalid username")
        String username,

        @Email(message = "Invalid email format")
        String email,

        @NotEmpty(message = "Invalid password")
        String password
){}
