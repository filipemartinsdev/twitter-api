package com.api.twitter.security.domain.service;

import com.api.twitter.security.application.dto.UserLoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public String generateToken(UserLoginRequest userLoginRequest);

    public String validateTokenAndGetUsername(String token);

    public String getTokenType();

    public Integer getExpirationOnSeconds();
}
