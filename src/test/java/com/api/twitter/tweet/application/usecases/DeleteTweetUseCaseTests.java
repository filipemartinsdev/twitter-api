package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTweetUseCaseTests {
    @Mock private TweetRepository tweetRepository;
    @Mock private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @InjectMocks private DeleteTweetUseCase deleteTweetUseCase;

    @Test @DisplayName("Should delete tweet successfully")
    void executeTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweet));
        doNothing().when(tweetRepository).deleteById(tweetId);

        // When
        deleteTweetUseCase.execute(tweetId);

        // Then
        verify(tweetRepository).findById(tweetId);
        verify(tweetRepository).deleteById(tweetId);
    }

    @Test @DisplayName("Should throw NotFoundException when tweet not found")
    void executeTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        try {
            deleteTweetUseCase.execute(tweetId);
        } catch (NotFoundException e) {
            verify(tweetRepository).findById(tweetId);
            verify(tweetRepository, never()).deleteById(any());
        }
    }
}