package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Deprecated
public class UpdateUserFollowersCountUseCase {
    @Autowired
    private UserProfileRepository userRepository;

    @Deprecated
    public void incrementFollowersOfUser(UUID id){
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
//        user.incrementFollowersCount();
        userRepository.save(user);
    }

    @Deprecated
    public void decrementFollowersOfUser(UUID id){
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
//        user.decrementFollowersCount();
        userRepository.save(user);
    }

    @Deprecated
    public void incrementFollowingOfUser(UUID id){
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
//        user.incrementFollowingCount();
        userRepository.save(user);
    }

    @Deprecated
    public void decrementFollowingOfUser(UUID id){
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
//        user.decrementFollowingCount();
        userRepository.save(user);
    }
}
