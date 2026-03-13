package com.api.twitter.security.infrastructure.web

import com.api.twitter.common.dto.ApiResponseDTO
import com.api.twitter.security.application.dto.TokenResponse
import com.api.twitter.security.application.dto.UserLoginRequest
import com.api.twitter.security.application.dto.UserRegisterRequest
import com.api.twitter.security.application.usecases.LoginUseCase
import com.api.twitter.security.application.usecases.RegisterUserCredentialsUseCase
import com.api.twitter.security.docs.AuthControllerDocs
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/auth")
class AuthController(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUserCredentialsUseCase
) : AuthControllerDocs {
    @PostMapping("/login")
    override fun login(@RequestBody @Valid userLoginRequest: @Valid UserLoginRequest): ResponseEntity<ApiResponseDTO<TokenResponse?>?> {
        val tokenResponse = loginUseCase.loginUserAndGetToken(userLoginRequest)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body<ApiResponseDTO<TokenResponse?>?>(ApiResponseDTO.success<TokenResponse?>(tokenResponse))
    }

    @PostMapping("/register")
    override fun register(@RequestBody @Valid userRegisterRequest: @Valid UserRegisterRequest): ResponseEntity<Void?> {
        registerUseCase.execute(
            userRegisterRequest.username,
            userRegisterRequest.email,
            userRegisterRequest.password
        )

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build<Void?>()
    }
}
