package com.api.twitter.tweet.application.usecases;

import com.api.twitter.tweet.application.dto.TweetRequest;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.dto.TweetUserResponse;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.user.application.usecases.GetUserEntityStrictUseCase;
import com.api.twitter.user.domain.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTweetUseCaseTests {
    @Mock private TweetRepository tweetRepository;
    @Mock private GetUserEntityStrictUseCase getUserEntityStrictUseCase;
    @Mock private TweetMapper tweetMapper;

    @InjectMocks private CreateTweetUseCase createTweetUseCase;

    @Test @DisplayName("Should create tweet successfully")
    void executeTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        String content = "This is a tweet content";
        TweetRequest tweetRequest = new TweetRequest(null, content);

        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername("username");

        Tweet tweet = new Tweet();
        tweet.setId(UUID.randomUUID());
        tweet.setUser(user);
        tweet.setContent(content);
        tweet.setCreatedAt(LocalDateTime.now());

        TweetResponse tweetResponse = new TweetResponse(
                tweet.getId(), null, content, LocalDateTime.now(), 0L, 0L, 0L,
                new TweetUserResponse(userId, "username"));

        when(getUserEntityStrictUseCase.execute(userId)).thenReturn(user);
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetMapper.toResponse(any(Tweet.class))).thenReturn(tweetResponse);

        // When
        TweetResponse result = createTweetUseCase.execute(userId, tweetRequest);

        // Then
        assertNotNull(result);
        verify(getUserEntityStrictUseCase).execute(userId);
        verify(tweetRepository).save(any(Tweet.class));
        verify(tweetMapper).toResponse(any(Tweet.class));
    }

    @Test @DisplayName("Should create reply tweet successfully")
    void executeTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID parentId = UUID.randomUUID();
        String content = "This is a reply";
        TweetRequest tweetRequest = new TweetRequest(parentId, content);

        UserProfile user = new UserProfile();
        user.setUserId(userId);
        user.setUsername("username");

        Tweet tweet = new Tweet();
        tweet.setId(UUID.randomUUID());
        tweet.setParentId(parentId);
        tweet.setUser(user);
        tweet.setContent(content);
        tweet.setCreatedAt(LocalDateTime.now());

        TweetResponse tweetResponse = new TweetResponse(
                tweet.getId(), parentId, content, LocalDateTime.now(), 0L, 0L, 0L,
                new TweetUserResponse(userId, "username"));

        when(getUserEntityStrictUseCase.execute(userId)).thenReturn(user);
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetMapper.toResponse(any(Tweet.class))).thenReturn(tweetResponse);

        // When
        TweetResponse result = createTweetUseCase.execute(userId, tweetRequest);

        // Then
        assertNotNull(result);
        verify(getUserEntityStrictUseCase).execute(userId);
        verify(tweetRepository).save(any(Tweet.class));
    }
}