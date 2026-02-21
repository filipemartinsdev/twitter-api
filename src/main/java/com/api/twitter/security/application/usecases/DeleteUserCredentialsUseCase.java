package com.api.twitter.security.application.usecases;

import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserCredentialsUseCase {
    private UserCredentialsRepository userCredentialsRepository;

    public DeleteUserCredentialsUseCase(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public void execute(UUID userId) {
        userCredentialsRepository.deleteById(userId);
    }
}
