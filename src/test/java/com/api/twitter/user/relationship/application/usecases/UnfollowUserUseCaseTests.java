package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnfollowUserUseCaseTests {
    @Mock private UserRelationshipRepository userRelationshipRepository;
    @Mock private VerifyUserUseCase verifyUserUseCase;
    @Mock private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks private UnfollowUserUseCase unfollowUserUseCase;

    @Test @DisplayName("Should unfollow user successfully")
    void executeTestCase1() {
        // Given
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId))
                .thenReturn(true);
        when(verifyUserUseCase.existsById(followerId)).thenReturn(true);
        when(verifyUserUseCase.existsById(followingId)).thenReturn(true);
        doNothing().when(userRelationshipRepository).deleteByFollowerUserIdAndFollowingUserId(followerId, followingId);

        // When
        unfollowUserUseCase.execute(followerId, followingId);

        // Then
        verify(userRelationshipRepository).existsByFollowerUserIdAndFollowingUserId(followerId, followingId);
        verify(verifyUserUseCase).existsById(followerId);
        verify(verifyUserUseCase).existsById(followingId);
        verify(userRelationshipRepository).deleteByFollowerUserIdAndFollowingUserId(followerId, followingId);
    }

    @Test @DisplayName("Should throw BadRequestException when not following user")
    void executeTestCase2() {
        // Given
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId))
                .thenReturn(false);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            unfollowUserUseCase.execute(followerId, followingId);
        });

        verify(userRelationshipRepository, never()).deleteByFollowerUserIdAndFollowingUserId(any(), any());
    }

    @Test @DisplayName("Should throw NotFoundException when user not found")
    void executeTestCase3() {
        // Given
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId))
                .thenReturn(true);
        when(verifyUserUseCase.existsById(followerId)).thenReturn(false);

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            unfollowUserUseCase.execute(followerId, followingId);
        });
    }
}