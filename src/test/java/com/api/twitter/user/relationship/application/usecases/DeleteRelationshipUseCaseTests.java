package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRelationshipUseCaseTests {
    @Mock private UserRelationshipRepository userRelationshipRepository;
    @Mock private VerifyUserUseCase verifyUserUseCase;

    @InjectMocks private DeleteRelationshipUseCase deleteRelationshipUseCase;

    @Test @DisplayName("Should delete all relationships by user id")
    void deleteAllByUserIdTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();

        when(verifyUserUseCase.existsById(userId)).thenReturn(true);
        doNothing().when(userRelationshipRepository).deleteAllByUserId(userId);

        // When
        deleteRelationshipUseCase.deleteAllByUserId(userId);

        // Then
        verify(verifyUserUseCase).existsById(userId);
        verify(userRelationshipRepository).deleteAllByUserId(userId);
    }

    @Test @DisplayName("Should throw NotFoundException when user not found")
    void deleteAllByUserIdTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();

        when(verifyUserUseCase.existsById(userId)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            deleteRelationshipUseCase.deleteAllByUserId(userId);
        });

        verify(userRelationshipRepository, never()).deleteAllByUserId(any());
    }
}