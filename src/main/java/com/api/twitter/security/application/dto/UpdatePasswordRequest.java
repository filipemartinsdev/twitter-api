package com.api.twitter.security.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UpdatePasswordRequest (
        @NotEmpty String password
){
}
