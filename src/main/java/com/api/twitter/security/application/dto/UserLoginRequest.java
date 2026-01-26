package com.api.twitter.security.application.dto;

public record UserLoginRequest (
        String username,
        String password
){}
