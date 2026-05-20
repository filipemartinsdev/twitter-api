package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.user.domain.UserProfile;
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
class VerifyTweetUseCaseTests {
    @Mock private TweetRepository tweetRepository;

    @InjectMocks private VerifyTweetUseCase verifyTweetUseCase;

    @Test @DisplayName("Should return true when tweet belongs to user")
    void isTweetFromUserTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));

        // When
        boolean result = verifyTweetUseCase.isTweetFromUser(tweetId, userId);

        // Then
        assertTrue(result);
        verify(tweetRepository).findById(tweetId);
    }

    @Test @DisplayName("Should return false when tweet does not belong to user")
    void isTweetFromUserTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID differentUserId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));

        // When
        boolean result = verifyTweetUseCase.isTweetFromUser(tweetId, differentUserId);

        // Then
        assertFalse(result);
        verify(tweetRepository).findById(tweetId);
    }

    @Test @DisplayName("Should throw NotFoundException when tweet not found")
    void isTweetFromUserTestCase3() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            verifyTweetUseCase.isTweetFromUser(tweetId, userId);
        });
    }
}