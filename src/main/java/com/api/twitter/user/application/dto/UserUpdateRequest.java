package com.api.twitter.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.openapitools.jackson.nullable.JsonNullable;

public record UserUpdateRequest(
        JsonNullable<@NotEmpty String> username,
        JsonNullable<@Email String> email,
        JsonNullable<@NotEmpty String> description
) {
}
