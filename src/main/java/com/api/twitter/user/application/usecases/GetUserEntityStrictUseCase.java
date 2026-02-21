package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This useCase should not be used outside of UserProfile domain
 */
@Component
public class GetUserEntityStrictUseCase {
    @Autowired
    private UserProfileRepository userRepository;

    public UserProfile execute(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
    }
}
