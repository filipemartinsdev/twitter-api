package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultRelationshipsCountTests {
    @Mock private UserRelationshipRepository userRelationshipRepository;

    @InjectMocks private ConsultRelationshipsCount consultRelationshipsCount;

    @Test @DisplayName("Should return followers count")
    void getFollowersCountOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        long expectedCount = 10L;

        when(userRelationshipRepository.getFollowersCountOfUser(userId)).thenReturn(expectedCount);

        // When
        long result = consultRelationshipsCount.getFollowersCountOfUser(userId);

        // Then
        assertEquals(expectedCount, result);
        verify(userRelationshipRepository).getFollowersCountOfUser(userId);
    }

    @Test @DisplayName("Should return following count")
    void getFollowingCountOfUserTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        long expectedCount = 5L;

        when(userRelationshipRepository.getFollowingCountOfUser(userId)).thenReturn(expectedCount);

        // When
        long result = consultRelationshipsCount.getFollowingCountOfUser(userId);

        // Then
        assertEquals(expectedCount, result);
        verify(userRelationshipRepository).getFollowingCountOfUser(userId);
    }
}