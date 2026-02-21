package com.api.twitter.security.application.usecases;

import com.api.twitter.common.dto.AuthenticatedUser;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.domain.model.UserCredentials;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class GetAuthenticatedUserUseCase {
    public Optional<AuthenticatedUser> execute(){
        var auth = (UserCredentials) SecurityContextHolder.getContext().getAuthentication();
        try {
            var authUser = new AuthenticatedUser(auth.getUserId(), auth.getUsername());
            return Optional.of(authUser);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }

    public UUID getId(){
        try {
            UUID authenticatedUserId = ((UserCredentials)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            return authenticatedUserId;
        } catch (Exception e){
            throw new UnauthorizedException("User not authenticated");
        }
    }
}
