package com.api.twitter.security.application.dto;

public record UserRegisterRequest (
        String username,
        String email,
        String password
){}
