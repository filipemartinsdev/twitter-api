package com.api.twitter.user.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String username,
        String email,
        Long followersCount,
        Long followingCount,
        Long tweetsCount
) {}
