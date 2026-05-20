package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.domain.TweetLike;
import com.api.twitter.tweet.domain.TweetLikeId;
import com.api.twitter.tweet.infrastructure.persistence.TweetLikeRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractTweetUseCaseTests {
    @Mock private TweetRepository tweetRepository;
    @Mock private TweetLikeRepository tweetLikeRepository;

    @InjectMocks private InteractTweetUseCase interactTweetUseCase;

    @Test @DisplayName("Should like tweet successfully")
    void likeTweetAsUserTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        when(tweetLikeRepository.existsById(any(TweetLikeId.class))).thenReturn(false);
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetLikeRepository.save(any(TweetLike.class))).thenReturn(new TweetLike());

        // When
        interactTweetUseCase.likeTweetAsUser(tweetId, userId);

        // Then
        verify(tweetRepository).findById(tweetId);
        verify(tweetLikeRepository).existsById(any(TweetLikeId.class));
        verify(tweetRepository).save(any(Tweet.class));
        verify(tweetLikeRepository).save(any(TweetLike.class));
    }

    @Test @DisplayName("Should throw NotFoundException when tweet not found for like")
    void likeTweetAsUserTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            interactTweetUseCase.likeTweetAsUser(tweetId, userId);
        });
    }

    @Test @DisplayName("Should throw BadRequestException when already liked")
    void likeTweetAsUserTestCase3() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        when(tweetLikeRepository.existsById(any(TweetLikeId.class))).thenReturn(true);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            interactTweetUseCase.likeTweetAsUser(tweetId, userId);
        });

        verify(tweetLikeRepository, never()).save(any());
    }

    @Test @DisplayName("Should unlike tweet successfully")
    void unlikeTweetAsUserTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        when(tweetLikeRepository.existsById(any(TweetLikeId.class))).thenReturn(true);
        doNothing().when(tweetLikeRepository).deleteById(any(TweetLikeId.class));

        // When
        interactTweetUseCase.unlikeTweetAsUser(tweetId, userId);

        // Then
        verify(tweetRepository).findById(tweetId);
        verify(tweetLikeRepository).existsById(any(TweetLikeId.class));
        verify(tweetLikeRepository).deleteById(any(TweetLikeId.class));
    }

    @Test @DisplayName("Should throw NotFoundException when tweet not found for unlike")
    void unlikeTweetAsUserTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> {
            interactTweetUseCase.unlikeTweetAsUser(tweetId, userId);
        });
    }

    @Test @DisplayName("Should throw BadRequestException when not liked yet")
    void unlikeTweetAsUserTestCase3() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserProfile user = new UserProfile();
        user.setUserId(userId);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setUser(user);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        when(tweetLikeRepository.existsById(any(TweetLikeId.class))).thenReturn(false);

        // When & Then
        assertThrows(BadRequestException.class, () -> {
            interactTweetUseCase.unlikeTweetAsUser(tweetId, userId);
        });
    }
}