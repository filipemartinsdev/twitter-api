package com.api.twitter.security.application.dto

data class TokenResponse(
    val token: String?,
    val tokenType: String?,
    val expiresOnSeconds: Int?
)
