package com.api.twitter.user.application.usecases;

import com.api.twitter.common.events.UserDeletedEvent;
import com.api.twitter.user.application.exception.UserNotExists;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import jakarta.transaction.Transactional;
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
    private UserProfileRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "usersByPageSizeSort", allEntries = true),
            @CacheEvict(value = "userQueryByNumberSizeSort", allEntries = true),
            @CacheEvict(value = "userById", key = "#userId.toString()")
    })
    public void deleteById(UUID id){
        if (!userRepository.existsById(id))
            throw new UserNotExists("UserProfile not found");

        userRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new UserDeletedEvent(id, this));
    }
}
