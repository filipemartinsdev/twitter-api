package com.api.twitter.security.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import com.api.twitter.security.application.usecases.LoginUseCase;
import com.api.twitter.security.application.usecases.RegisterUserCredentialsUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {
    private LoginUseCase loginUseCase;
    private RegisterUserCredentialsUseCase registerUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUserCredentialsUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        TokenResponse tokenResponse = loginUseCase.loginUserAndGetToken(userLoginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(tokenResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        registerUseCase.execute(
                userRegisterRequest.username(),
                userRegisterRequest.email(),
                userRegisterRequest.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
