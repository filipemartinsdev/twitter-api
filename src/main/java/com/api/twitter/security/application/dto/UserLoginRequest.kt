package com.api.twitter.security.application.dto

import jakarta.validation.constraints.NotEmpty

data class UserLoginRequest(
    val username: @NotEmpty String?,
    val password: @NotEmpty String?
)
