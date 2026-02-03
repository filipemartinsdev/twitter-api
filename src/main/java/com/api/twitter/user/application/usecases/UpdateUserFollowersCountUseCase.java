package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Deprecated
public class UpdateUserFollowersCountUseCase {
    @Autowired
    private UserRepository userRepository;

    @Deprecated
    public void incrementFollowersOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
//        user.incrementFollowersCount();
        userRepository.save(user);
    }

    @Deprecated
    public void decrementFollowersOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
//        user.decrementFollowersCount();
        userRepository.save(user);
    }

    @Deprecated
    public void incrementFollowingOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
//        user.incrementFollowingCount();
        userRepository.save(user);
    }

    @Deprecated
    public void decrementFollowingOfUser(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
//        user.decrementFollowingCount();
        userRepository.save(user);
    }
}
