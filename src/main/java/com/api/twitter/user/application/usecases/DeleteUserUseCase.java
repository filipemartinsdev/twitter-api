package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.exception.UserNotExists;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

// TODO: when user not exists, is returning 500 instead of 404, fix it
// cause: authentication error

@Component
public class DeleteUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Caching(evict = {
            @CacheEvict(value = "userById", key = "#id.toString()"),
            @CacheEvict(value = "userPages", allEntries = true)
    })
    public void deleteById(UUID id){
        if (!userRepository.existsById(id))
            throw new UserNotExists("User not found");

        userRepository.deleteById(id);
    }
}
