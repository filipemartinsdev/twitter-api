package com.api.twitter.user.application.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record UserProfileResponse (
        UUID userId,
        String username,
        String email,
        String description
) implements Serializable {
}
