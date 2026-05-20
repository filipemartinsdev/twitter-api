package com.api.twitter.security.application.usecases;

import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.domain.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTests {
    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenService tokenService;
    @Mock private Authentication authentication;

    @InjectMocks private LoginUseCase loginUseCase;

    @Test @DisplayName("Should login user and return token successfully")
    void loginUserAndGetTokenTestCase1() {
        // Given
        String username = "username";
        String password = "password";
        UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
        String token = "jwt-token";
        String tokenType = "Bearer";
        Integer expiration = 3600;

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateToken(any(UserLoginRequest.class))).thenReturn(token);
        when(tokenService.getTokenType()).thenReturn(tokenType);
        when(tokenService.getExpirationOnSeconds()).thenReturn(expiration);

        // When
        TokenResponse response = loginUseCase.loginUserAndGetToken(userLoginRequest);

        // Then
        assertEquals(token, response.token());
        assertEquals(tokenType, response.tokenType());
        assertEquals(expiration, response.expiresOnSeconds());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(userLoginRequest);
    }
}