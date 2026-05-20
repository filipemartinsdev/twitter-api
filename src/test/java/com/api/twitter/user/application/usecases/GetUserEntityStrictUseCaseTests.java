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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserEntityStrictUseCaseTests {
    @Mock private UserProfileRepository userRepository;

    @InjectMocks private GetUserEntityStrictUseCase getUserEntityStrictUseCase;

    @Test @DisplayName("Should return user when found")
    void executeTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        userProfile.setUsername("username");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userProfile));

        // When
        UserProfile result = getUserEntityStrictUseCase.execute(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("username", result.getUsername());
        verify(userRepository).findById(userId);
    }

    @Test @DisplayName("Should throw NotFoundException when user not found")
    void executeTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            getUserEntityStrictUseCase.execute(userId);
        });

        verify(userRepository).findById(userId);
    }
}