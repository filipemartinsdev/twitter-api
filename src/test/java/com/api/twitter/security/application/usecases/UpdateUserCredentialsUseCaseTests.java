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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserCredentialsUseCaseTests {
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks private UpdateUserCredentialsUseCase updateUserCredentialsUseCase;

    @Test @DisplayName("Should update username and email successfully")
    void executeTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        String newUsername = "newUsername";
        String newEmail = "newemail@gmail.com";

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setUsername("oldUsername");
        userCredentials.setEmail("oldemail@gmail.com");
        userCredentials.setPassword("validpassword123");

        when(userCredentialsRepository.findById(userId))
                .thenReturn(Optional.of(userCredentials));
        when(userCredentialsRepository.save(any(UserCredentials.class)))
                .thenReturn(userCredentials);

        // When
        updateUserCredentialsUseCase.execute(userId, newUsername, newEmail);

        // Then
        verify(userCredentialsRepository).findById(userId);
        verify(userCredentialsRepository).save(any(UserCredentials.class));
    }

    @Test @DisplayName("Should throw BadRequestException when user not found")
    void executeTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        String newUsername = "newUsername";
        String newEmail = "newemail@gmail.com";

        when(userCredentialsRepository.findById(userId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            updateUserCredentialsUseCase.execute(userId, newUsername, newEmail);
        });

        verify(userCredentialsRepository, never()).save(any());
    }

    @Test @DisplayName("Should update password successfully")
    void updatePasswordTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword123";

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setPassword("oldEncodedPassword");

        when(userCredentialsRepository.findById(userId))
                .thenReturn(Optional.of(userCredentials));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userCredentialsRepository.save(any(UserCredentials.class)))
                .thenReturn(userCredentials);

        // When
        updateUserCredentialsUseCase.updatePassword(userId, newPassword);

        // Then
        verify(userCredentialsRepository).findById(userId);
        verify(passwordEncoder).encode(newPassword);
        verify(userCredentialsRepository).save(any(UserCredentials.class));
    }

    @Test @DisplayName("Should throw RuntimeException when user not found for password update")
    void updatePasswordTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword123";

        when(userCredentialsRepository.findById(userId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            updateUserCredentialsUseCase.updatePassword(userId, newPassword);
        });

        verify(userCredentialsRepository, never()).save(any());
    }
}