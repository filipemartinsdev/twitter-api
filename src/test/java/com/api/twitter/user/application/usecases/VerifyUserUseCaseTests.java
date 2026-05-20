package com.api.twitter.user.application.usecases;

import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyUserUseCaseTests {
    @Mock private UserProfileRepository userRepository;

    @InjectMocks private VerifyUserUseCase verifyUserUseCase;

    @Test @DisplayName("Should return true when user exists by id")
    void existsByIdTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        boolean result = verifyUserUseCase.existsById(userId);

        // Then
        assertTrue(result);
        verify(userRepository).existsById(userId);
    }

    @Test @DisplayName("Should return false when user does not exist by id")
    void existsByIdTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);

        // When
        boolean result = verifyUserUseCase.existsById(userId);

        // Then
        assertFalse(result);
        verify(userRepository).existsById(userId);
    }

    @Test @DisplayName("Should return true when username exists")
    void existsByUsernameTestCase1() {
        // Given
        String username = "existingUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // When
        boolean result = verifyUserUseCase.existsByUsername(username);

        // Then
        assertTrue(result);
        verify(userRepository).existsByUsername(username);
    }

    @Test @DisplayName("Should return false when username does not exist")
    void existsByUsernameTestCase2() {
        // Given
        String username = "newUser";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        // When
        boolean result = verifyUserUseCase.existsByUsername(username);

        // Then
        assertFalse(result);
        verify(userRepository).existsByUsername(username);
    }

    @Test @DisplayName("Should return true when email exists")
    void existsByEmailTestCase1() {
        // Given
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When
        boolean result = verifyUserUseCase.existsByEmail(email);

        // Then
        assertTrue(result);
        verify(userRepository).existsByEmail(email);
    }

    @Test @DisplayName("Should return false when email does not exist")
    void existsByEmailTestCase2() {
        // Given
        String email = "new@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // When
        boolean result = verifyUserUseCase.existsByEmail(email);

        // Then
        assertFalse(result);
        verify(userRepository).existsByEmail(email);
    }
}