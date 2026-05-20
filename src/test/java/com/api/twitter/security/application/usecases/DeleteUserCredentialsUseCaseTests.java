package com.api.twitter.security.application.usecases;

import com.api.twitter.security.infrastructure.persistence.UserCredentialsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserCredentialsUseCaseTests {
    @Mock private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks private DeleteUserCredentialsUseCase deleteUserCredentialsUseCase;

    @Test @DisplayName("Should delete user by userId successfully")
    void executeTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();

        doNothing().when(userCredentialsRepository).deleteById(userId);

        // When
        deleteUserCredentialsUseCase.execute(userId);

        // Then
        verify(userCredentialsRepository).deleteById(userId);
    }
}