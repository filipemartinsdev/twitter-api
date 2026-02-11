package com.api.twitter.security.domain.service;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import com.api.twitter.security.infrastructure.TokenService;
import com.api.twitter.user.application.usecases.CreateUserUseCase;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final CreateUserUseCase createUserUseCase;

    private final VerifyUserUseCase verifyUserUseCase;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegisterRequest userRegisterRequest){
        createUserUseCase.execute(
                userRegisterRequest.username(),
                userRegisterRequest.email(),
                userRegisterRequest.password()
        );
    }

    public TokenResponse loginUserAndGetToken(UserLoginRequest userLoginRequest){
        var userPassToken = new UsernamePasswordAuthenticationToken(userLoginRequest.username(), userLoginRequest.password());
        var auth = authenticationManager.authenticate(userPassToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = tokenService.generateToken(userLoginRequest);
        return new TokenResponse(token, tokenService.getTokenType(), tokenService.getExpirationOnSeconds());
    }
}
