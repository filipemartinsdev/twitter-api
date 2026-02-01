package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateUserFollowersCountUseCase {
    @Autowired
    private UserRepository userRepository;

    public void incrementFollowersOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.incrementFollowersCount();
        userRepository.save(user);
    }

    public void decrementFollowersOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.decrementFollowersCount();
        userRepository.save(user);
    }

    public void incrementFollowingOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.incrementFollowingCount();
        userRepository.save(user);
    }

    public void decrementFollowingOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.decrementFollowingCount();
        userRepository.save(user);
    }
}
