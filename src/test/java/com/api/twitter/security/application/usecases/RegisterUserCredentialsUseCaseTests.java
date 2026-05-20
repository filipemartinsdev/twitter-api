package com.api.twitter.security.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.security.domain.model.UserCredentials;
import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RegisterUserCredentialsUseCaseTests {
    @Mock private UserCredentialsRepository userCredentialsRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks private RegisterUserCredentialsUseCase registerUserCredentialsUseCase;

    @Test @DisplayName("Should register new user successfully")
    void executeTestCase1() {
//        Given
        String username = "username";
        String password = "password";
        String email = "email@gmail.com";

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(UUID.randomUUID());
        userCredentials.setUsername(username);
        userCredentials.setPassword(password);
        userCredentials.setEmail(email);

        when(userCredentialsRepository.existsByEmail(any()))
                .thenReturn(false);
        when(userCredentialsRepository.existsByUsername(any()))
                .thenReturn(false);
        when(passwordEncoder.encode(any()))
                .thenReturn("password");
        when(userCredentialsRepository.save(any(UserCredentials.class)))
                .thenReturn(userCredentials);

        doNothing().when(applicationEventPublisher)
                .publishEvent(any());

//        When

        registerUserCredentialsUseCase.execute(username, email, password);

//        Then
        verify(userCredentialsRepository).existsByEmail(email);
        verify(userCredentialsRepository).existsByUsername(username);
        verify(userCredentialsRepository).save(any());
        verify(applicationEventPublisher).publishEvent(any());
    }

    @Test @DisplayName("Should throw BadRequestException if username is already in use")
    void executeTestCase2() {
//        Given
        String username = "username";
        String password = "password";
        String email = "email@gmail.com";

        when(userCredentialsRepository.existsByEmail(any()))
                .thenReturn(true);
        when(userCredentialsRepository.existsByUsername(any()))
                .thenReturn(false);
//        When & Then

        assertThrows(BadRequestException.class, () -> {
                registerUserCredentialsUseCase.execute(username, email, password);
        });

        verify(userCredentialsRepository, never()).save(any());
    }

@Test @DisplayName("Should throw BadRequestException if email is already in use")
    void executeTestCase3() {
        //        Given
        String username = "username";
        String password = "password";
        String email = "email@gmail.com";

        when(userCredentialsRepository.existsByUsername(any()))
                .thenReturn(false);
        when(userCredentialsRepository.existsByEmail(any()))
                .thenReturn(true);
 //        When & Then

        assertThrows(BadRequestException.class, () -> {
            registerUserCredentialsUseCase.execute(username, email, password);
        });

        verify(userCredentialsRepository).existsByUsername(username);
        verify(userCredentialsRepository).existsByEmail(email);
        verify(userCredentialsRepository, never()).save(any());
    }
}