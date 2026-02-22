package com.api.twitter.security.application.usecases;

import com.api.twitter.common.dto.AuthenticatedUser;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.domain.model.UserCredentials;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetAuthenticatedUserUseCase {
    public Optional<AuthenticatedUser> execute(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials userCredentials = (UserCredentials) auth.getPrincipal();
            return Optional.of(new AuthenticatedUser(userCredentials.getUserId(), userCredentials.getUsername()));
        }
        catch (Exception e){
            return Optional.empty();
        }
    }

    public UUID getId(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserCredentials userCredentials = (UserCredentials) auth.getPrincipal();
            return userCredentials.getUserId();
        } catch (Exception e){
            throw new UnauthorizedException("User not authenticated");
        }
    }
}
