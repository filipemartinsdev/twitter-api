package com.api.twitter.security.application.usecases;

import com.api.twitter.common.dto.AuthenticatedUser;
import com.api.twitter.security.domain.model.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetAuthenticatedUserUseCase {
    public AuthenticatedUser execute(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authUser = new AuthenticatedUser(userDetails.getId(), userDetails.getUsername());
        return authUser;
    }
}
