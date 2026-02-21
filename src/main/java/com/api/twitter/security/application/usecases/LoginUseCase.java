package com.api.twitter.security.application.usecases;

import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.domain.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public LoginUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public TokenResponse loginUserAndGetToken(UserLoginRequest userLoginRequest){
        var userPassToken = new UsernamePasswordAuthenticationToken(userLoginRequest.username(), userLoginRequest.password());
        var auth = authenticationManager.authenticate(userPassToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = tokenService.generateToken(userLoginRequest);
        return new TokenResponse(token, tokenService.getTokenType(), tokenService.getExpirationOnSeconds());
    }
}
