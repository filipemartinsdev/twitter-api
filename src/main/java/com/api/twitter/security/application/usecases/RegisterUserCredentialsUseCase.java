package com.api.twitter.security.application.usecases;

import com.api.twitter.common.events.UserCreatedEvent;
import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.model.UserRole;
import com.api.twitter.security.domain.model.UserCredentials;
import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterUserCredentialsUseCase {
    private UserCredentialsRepository userCredentialsRepository;

    private PasswordEncoder passwordEncoder;

    private ApplicationEventPublisher applicationEventPublisher;

    public RegisterUserCredentialsUseCase(
            UserCredentialsRepository userCredentialsRepository,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void execute(String username, String email, String password){
        verifyIfUsernameOrEmailIsAlreadyInUse(username, email);

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(username);
        userCredentials.setEmail(email);
        userCredentials.setPassword(password);
        userCredentials.setRole(UserRole.USER);
        userCredentials.setCreatedAt(LocalDateTime.now());
        userCredentials.setPassword(password);
        userCredentials.validate();

        UserCredentials user = userCredentialsRepository.save(encodePassword(userCredentials));

        publishUserCreatedEvent(user);
    }

    private void verifyIfUsernameOrEmailIsAlreadyInUse(String username, String email){
        if(userCredentialsRepository.existsByUsername(username))
            throw new BadRequestException("This username is already in use");
        if(userCredentialsRepository.existsByEmail(email))
            throw new BadRequestException("This email is already in use");
    }

    private UserCredentials encodePassword(UserCredentials userCredentials){
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        return userCredentials;
    }

    private void publishUserCreatedEvent(UserCredentials user){
        applicationEventPublisher.publishEvent(
                new UserCreatedEvent(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        this
                )
        );
    }
}
