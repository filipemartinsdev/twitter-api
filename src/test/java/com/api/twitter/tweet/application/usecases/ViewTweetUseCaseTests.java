package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.tweet.application.exceptions.TweetNotFound;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.domain.TweetView;
import com.api.twitter.tweet.domain.TweetViewId;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.tweet.infrastructure.persistence.TweetViewRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewTweetUseCaseTests {
    @Mock private TweetViewRepository tweetViewRepository;
    @Mock private TweetRepository tweetRepository;

    @InjectMocks private ViewTweetUseCase viewTweetUseCase;

    @Test @DisplayName("Should view tweet successfully")
    void viewTweetAsUserTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile tweetOwner = new UserProfile();
        tweetOwner.setUserId(UUID.randomUUID());

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(tweetOwner);

        when(tweetViewRepository.existsById(any(TweetViewId.class))).thenReturn(false);
        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        when(tweetViewRepository.save(any(TweetView.class))).thenReturn(new TweetView());

        // When
        viewTweetUseCase.viewTweetAsUser(tweetId, userId);

        // Then
        verify(tweetViewRepository).existsById(any(TweetViewId.class));
        verify(tweetRepository).findById(tweetId);
        verify(tweetViewRepository).save(any(TweetView.class));
    }

    @Test @DisplayName("Should throw BadRequestException when already viewed")
    void viewTweetAsUserTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(tweetViewRepository.existsById(any(TweetViewId.class))).thenReturn(true);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            viewTweetUseCase.viewTweetAsUser(tweetId, userId);
        });

        verify(tweetRepository, never()).findById(any());
    }

    @Test @DisplayName("Should throw TweetNotFound when tweet not found")
    void viewTweetAsUserTestCase3() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(tweetViewRepository.existsById(any(TweetViewId.class))).thenReturn(false);
        when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TweetNotFound.class, () -> {
            viewTweetUseCase.viewTweetAsUser(tweetId, userId);
        });
    }

    @Test @DisplayName("Should throw BadRequestException when viewing own tweet")
    void viewTweetAsUserTestCase4() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID tweetId = UUID.randomUUID();

        UserProfile tweetOwner = new UserProfile();
        tweetOwner.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(tweetOwner);

        when(tweetViewRepository.existsById(any(TweetViewId.class))).thenReturn(false);
        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            viewTweetUseCase.viewTweetAsUser(tweetId, userId);
        });
    }
}