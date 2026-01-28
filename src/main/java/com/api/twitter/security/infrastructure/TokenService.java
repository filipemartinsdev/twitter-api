package com.api.twitter.security.infrastructure;

import com.api.twitter.security.application.dto.UserLoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface TokenService {
    public String generateToken(UserLoginRequest userLoginRequest);

    public String validateTokenAndGetUsername(String token);
}
