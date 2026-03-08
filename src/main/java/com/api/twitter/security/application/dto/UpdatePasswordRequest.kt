package com.api.twitter.security.application.dto

import jakarta.validation.constraints.NotEmpty

data class UpdatePasswordRequest(
    val password: @NotEmpty String
)
