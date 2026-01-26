package com.api.twitter.security.infrastructure.web;

import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
