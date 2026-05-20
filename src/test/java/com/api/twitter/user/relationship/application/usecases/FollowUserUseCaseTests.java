package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.application.usecases.GetUserEntityStrictUseCase;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.relationship.domain.UserRelationship;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowUserUseCaseTests {
    @Mock private UserRelationshipRepository userRelationshipRepository;
    @Mock private GetUserEntityStrictUseCase getUserEntityStrictUseCase;
    @Mock private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks private FollowUserUseCase followUserUseCase;

    @Test @DisplayName("Should follow user successfully")
    void executeTestCase1() {
        // Given
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();

        UserProfile follower = new UserProfile();
        follower.setUserId(followerId);
        follower.setUsername("follower");

        UserProfile following = new UserProfile();
        following.setUserId(followingId);
        following.setUsername("following");

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId))
                .thenReturn(false);
        when(getUserEntityStrictUseCase.execute(followerId)).thenReturn(follower);
        when(getUserEntityStrictUseCase.execute(followingId)).thenReturn(following);
        when(userRelationshipRepository.save(any(UserRelationship.class)))
                .thenReturn(new UserRelationship());

        // When
        followUserUseCase.execute(followerId, followingId);

        // Then
        verify(userRelationshipRepository).existsByFollowerUserIdAndFollowingUserId(followerId, followingId);
        verify(getUserEntityStrictUseCase).execute(followerId);
        verify(getUserEntityStrictUseCase).execute(followingId);
        verify(userRelationshipRepository).save(any(UserRelationship.class));
    }

    @Test @DisplayName("Should throw BadRequestException when already following")
    void executeTestCase2() {
        // Given
        UUID followerId = UUID.randomUUID();
        UUID followingId = UUID.randomUUID();

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId))
                .thenReturn(true);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            followUserUseCase.execute(followerId, followingId);
        });

        verify(userRelationshipRepository, never()).save(any());
    }

    @Test @DisplayName("Should throw BadRequestException when trying to follow yourself")
    void executeTestCase3() {
        // Given
        UUID userId = UUID.randomUUID();

        when(userRelationshipRepository.existsByFollowerUserIdAndFollowingUserId(userId, userId))
                .thenReturn(false);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            followUserUseCase.execute(userId, userId);
        });

        verify(userRelationshipRepository, never()).save(any());
    }
}