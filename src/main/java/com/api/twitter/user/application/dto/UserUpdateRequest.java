package com.api.twitter.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Optional;

public record UserUpdateRequest(
        Optional<String> username,
        Optional<@Email String> email,
        Optional<String> description
) {
}
