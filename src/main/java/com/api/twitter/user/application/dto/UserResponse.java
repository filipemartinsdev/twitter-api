package com.api.twitter.user.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse (
    UUID id,
    String username,
    String email,
    Long tweetsCount,
    Long followersCount,
    Long followingCount
){}
