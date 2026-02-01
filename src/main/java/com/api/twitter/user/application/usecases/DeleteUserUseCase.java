package com.api.twitter.user.application.usecases;

import com.api.twitter.common.domainevent.events.UserDeletedEvent;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void deleteById(UUID id){
        if (!userRepository.existsById(id))
            throw new NotFoundException("User not found");

        userRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new UserDeletedEvent(id, this));
    }
}
