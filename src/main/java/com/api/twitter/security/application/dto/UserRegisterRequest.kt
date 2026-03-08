package com.api.twitter.security.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class UserRegisterRequest(
    val username: @NotEmpty(message = "Invalid username") String,

    val email: @Email(message = "Invalid email format") String,

    val password: @NotEmpty(message = "Invalid password") String
)
