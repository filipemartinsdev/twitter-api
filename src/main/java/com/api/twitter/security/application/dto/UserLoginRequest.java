package com.api.twitter.security.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequest (
        @NotEmpty String username,
        @NotEmpty String password
){}
