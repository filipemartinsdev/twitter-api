package com.api.twitter.common.dto;

import java.util.UUID;

public record AuthenticatedUser (
        UUID id,
        String username
){}
