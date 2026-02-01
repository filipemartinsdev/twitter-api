package com.api.twitter.security.application.dto;

public record TokenResponse (
        String token,
        String tokenType,
        Integer expiresOnSeconds
){}
