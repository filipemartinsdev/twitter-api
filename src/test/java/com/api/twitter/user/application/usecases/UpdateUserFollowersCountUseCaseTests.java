package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserFollowersCountUseCaseTests {
    @Mock private UserProfileRepository userRepository;

    @InjectMocks private UpdateUserFollowersCountUseCase updateUserFollowersCountUseCase;

    @Test @DisplayName("Should increment followers count of user")
    void incrementFollowersOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userProfile));
        when(userRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        // When
        updateUserFollowersCountUseCase.incrementFollowersOfUser(userId);

        // Then
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(UserProfile.class));
    }

    @Test @DisplayName("Should throw NotFoundException when user not found for increment")
    void incrementFollowersOfUserTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            updateUserFollowersCountUseCase.incrementFollowersOfUser(userId);
        });
    }

    @Test @DisplayName("Should decrement followers count of user")
    void decrementFollowersOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userProfile));
        when(userRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        // When
        updateUserFollowersCountUseCase.decrementFollowersOfUser(userId);

        // Then
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(UserProfile.class));
    }

    @Test @DisplayName("Should throw NotFoundException when user not found for decrement")
    void decrementFollowersOfUserTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            updateUserFollowersCountUseCase.decrementFollowersOfUser(userId);
        });
    }

    @Test @DisplayName("Should increment following count of user")
    void incrementFollowingOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userProfile));
        when(userRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        // When
        updateUserFollowersCountUseCase.incrementFollowingOfUser(userId);

        // Then
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(UserProfile.class));
    }

    @Test @DisplayName("Should decrement following count of user")
    void decrementFollowingOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userProfile));
        when(userRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        // When
        updateUserFollowersCountUseCase.decrementFollowingOfUser(userId);

        // Then
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(UserProfile.class));
    }
}