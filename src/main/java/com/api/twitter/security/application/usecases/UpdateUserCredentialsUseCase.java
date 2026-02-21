package com.api.twitter.security.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.security.domain.model.UserCredentials;
import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateUserCredentialsUseCase {
    private PasswordEncoder passwordEncoder;
    private UserCredentialsRepository userCredentialsRepository;

    public UpdateUserCredentialsUseCase(PasswordEncoder passwordEncoder, UserCredentialsRepository userCredentialsRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public void execute(UUID userId, String username, String email) {
        UserCredentials userCredentials = userCredentialsRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));
        userCredentials.setUsername(username);
        userCredentials.setEmail(email);
        userCredentials.validate();
        userCredentialsRepository.save(userCredentials);
    }

    public void updatePassword(UUID userId, String password){
        UserCredentials userCredentials = userCredentialsRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userCredentials.setPassword(password);
        userCredentials.validatePassword();
        userCredentials.setPassword(passwordEncoder.encode(password));

        userCredentialsRepository.save(userCredentials);
    }
}
