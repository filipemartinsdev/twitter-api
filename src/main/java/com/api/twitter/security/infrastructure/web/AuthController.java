package com.api.twitter.security.infrastructure.web;

import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import com.api.twitter.security.domain.service.UserService;
import com.api.twitter.security.infrastructure.TokenService;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private VerifyUserUseCase verifyUserUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.loginUserAndGetToken(userLoginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest){
        userService.registerUser(userRegisterRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
