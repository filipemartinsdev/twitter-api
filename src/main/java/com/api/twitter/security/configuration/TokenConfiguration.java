package com.api.twitter.security.configuration;

import com.api.twitter.security.domain.service.JWTService;
import com.api.twitter.security.domain.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {
    @Bean
    public TokenService tokenService(){
        return new JWTService();
    }
}
