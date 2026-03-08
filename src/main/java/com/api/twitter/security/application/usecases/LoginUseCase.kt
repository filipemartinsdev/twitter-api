package com.api.twitter.security.application.usecases

import com.api.twitter.security.application.dto.TokenResponse
import com.api.twitter.security.application.dto.UserLoginRequest
import com.api.twitter.security.domain.service.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class LoginUseCase(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    fun loginUserAndGetToken(userLoginRequest: UserLoginRequest): TokenResponse {
        val userPassToken = UsernamePasswordAuthenticationToken(userLoginRequest.username, userLoginRequest.password)
        val auth = authenticationManager.authenticate(userPassToken)
        SecurityContextHolder.getContext().authentication = auth

        val token = tokenService.generateToken(userLoginRequest)

        return TokenResponse(token, tokenService.getTokenType(), tokenService.getExpirationOnSeconds())
    }
}
