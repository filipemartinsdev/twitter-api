package com.api.twitter.security.domain.service;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.InternalServerErrorException;
import com.api.twitter.security.application.dto.TokenResponse;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.exception.ExpiredTokenException;
import com.api.twitter.security.application.exception.InvalidTokenException;
import com.api.twitter.security.infrastructure.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JWTService implements TokenService {
    @Value(value = "${api.security.token.secret}")
    private String secret;

    @Value(value = "${api.security.token.issuer}")
    private String issuer;

    @Getter
    @Value(value = "${api.security.token.expiration-seconds}")
    private Integer expirationOnSeconds;

    @Getter
    private final String tokenType = "Bearer";

    @Override
    public String generateToken(UserLoginRequest userLoginRequest) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            final String TOKEN = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userLoginRequest.username())
                    .withExpiresAt(getExpireTime())
                    .sign(algorithm);
            return TOKEN;
        } catch (JWTCreationException exception){
            throw new InternalServerErrorException("Error creating token", exception);
        }
    }

    private Instant getExpireTime(){
        return LocalDateTime.now().plusSeconds(expirationOnSeconds).toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String validateTokenAndGetUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer(issuer)
                    .build();

            String username = verifier.verify(token).getSubject();
            return username;
        }
        catch (TokenExpiredException exception){
            throw new ExpiredTokenException("Token is expired");
        }
        catch (RuntimeException exception){
            throw new InvalidTokenException("Invalid token");
        }
    }
}
