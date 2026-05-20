package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.tweet.application.dto.TweetAndCounts;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.dto.TweetUserResponse;
import com.api.twitter.tweet.application.exceptions.TweetNotFound;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListTweetsUseCaseTests {
    @Mock private TweetRepository tweetRepository;
    @Mock private TweetMapper tweetMapper;

    @InjectMocks private ListTweetsUseCase listTweetsUseCase;

    @Test @DisplayName("Should return tweet by id")
    void getByIdTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        TweetAndCounts tweetAndCounts = mock(TweetAndCounts.class);
        lenient().when(tweetAndCounts.getId()).thenReturn(tweetId);
        lenient().when(tweetAndCounts.getParentId()).thenReturn(null);
        lenient().when(tweetAndCounts.getContent()).thenReturn("content");
        lenient().when(tweetAndCounts.getCreatedAt()).thenReturn(LocalDateTime.now());
        lenient().when(tweetAndCounts.getViewsCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getLikesCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getCommentsCount()).thenReturn(1L);

        com.api.twitter.user.domain.UserProfile userProfile = mock(com.api.twitter.user.domain.UserProfile.class);
        lenient().when(userProfile.getUserId()).thenReturn(userId);
        lenient().when(userProfile.getUsername()).thenReturn("username");
        lenient().when(tweetAndCounts.getUser()).thenReturn(userProfile);

        TweetResponse tweetResponse = new TweetResponse(
                tweetId, null, "content", LocalDateTime.now(), 1L, 1L, 1L,
                new TweetUserResponse(userId, "username"));

        when(tweetRepository.findTweetAndCountsById(tweetId)).thenReturn(Optional.of(tweetAndCounts));
        when(tweetMapper.toResponse(any(TweetAndCounts.class))).thenReturn(tweetResponse);

        // When
        TweetResponse result = listTweetsUseCase.getById(tweetId);

        // Then
        assertNotNull(result);
        verify(tweetRepository).findTweetAndCountsById(tweetId);
    }

    @Test @DisplayName("Should throw TweetNotFound when tweet not found")
    void getByIdTestCase2() {
        // Given
        UUID tweetId = UUID.randomUUID();

        when(tweetRepository.findTweetAndCountsById(tweetId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TweetNotFound.class, () -> {
            listTweetsUseCase.getById(tweetId);
        });
    }

    @Test @DisplayName("Should return list of tweets")
    void listAllTestCase1() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        TweetAndCounts tweetAndCounts = mock(TweetAndCounts.class);
        UUID tweetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        lenient().when(tweetAndCounts.getId()).thenReturn(tweetId);
        lenient().when(tweetAndCounts.getParentId()).thenReturn(null);
        lenient().when(tweetAndCounts.getContent()).thenReturn("content");
        lenient().when(tweetAndCounts.getCreatedAt()).thenReturn(LocalDateTime.now());
        lenient().when(tweetAndCounts.getViewsCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getLikesCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getCommentsCount()).thenReturn(1L);

        com.api.twitter.user.domain.UserProfile userProfile = mock(com.api.twitter.user.domain.UserProfile.class);
        lenient().when(userProfile.getUserId()).thenReturn(userId);
        lenient().when(userProfile.getUsername()).thenReturn("username");
        lenient().when(tweetAndCounts.getUser()).thenReturn(userProfile);

        Page<TweetAndCounts> page = new PageImpl<>(List.of(tweetAndCounts), pageable, 1);
        TweetResponse tweetResponse = new TweetResponse(
                tweetId, null, "content", LocalDateTime.now(), 1L, 1L, 1L,
                new TweetUserResponse(userId, "username"));

        when(tweetRepository.findAllTweetAndCounts(pageable)).thenReturn(page);
        when(tweetMapper.toResponse(any(TweetAndCounts.class))).thenReturn(tweetResponse);

        // When
        PagedResponse<TweetResponse> result = listTweetsUseCase.listAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(tweetRepository).findAllTweetAndCounts(pageable);
    }

    @Test @DisplayName("Should return tweets by user id")
    void listAllTweetsByUserIdTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        TweetAndCounts tweetAndCounts = mock(TweetAndCounts.class);
        UUID tweetId = UUID.randomUUID();

        lenient().when(tweetAndCounts.getId()).thenReturn(tweetId);
        lenient().when(tweetAndCounts.getParentId()).thenReturn(null);
        lenient().when(tweetAndCounts.getContent()).thenReturn("content");
        lenient().when(tweetAndCounts.getCreatedAt()).thenReturn(LocalDateTime.now());
        lenient().when(tweetAndCounts.getViewsCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getLikesCount()).thenReturn(1L);
        lenient().when(tweetAndCounts.getCommentsCount()).thenReturn(1L);

        com.api.twitter.user.domain.UserProfile userProfile = mock(com.api.twitter.user.domain.UserProfile.class);
        lenient().when(userProfile.getUserId()).thenReturn(userId);
        lenient().when(userProfile.getUsername()).thenReturn("username");
        lenient().when(tweetAndCounts.getUser()).thenReturn(userProfile);

        Page<TweetAndCounts> page = new PageImpl<>(List.of(tweetAndCounts), pageable, 1);
        TweetResponse tweetResponse = new TweetResponse(
                tweetId, null, "content", LocalDateTime.now(), 1L, 1L, 1L,
                new TweetUserResponse(userId, "username"));

        when(tweetRepository.findAllTweetAndCountsByUserId(userId, pageable)).thenReturn(page);
        when(tweetMapper.toResponse(any(TweetAndCounts.class))).thenReturn(tweetResponse);

        // When
        PagedResponse<TweetResponse> result = listTweetsUseCase.listAllTweetsByUserId(userId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(tweetRepository).findAllTweetAndCountsByUserId(userId, pageable);
    }

    @Test @DisplayName("Should return comments by tweet id")
    void listAllCommentsByTweetIdTestCase1() {
        // Given
        UUID tweetId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);

        TweetAndCounts tweetAndCounts = mock(TweetAndCounts.class);
        UUID commentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        lenient().when(tweetAndCounts.getId()).thenReturn(commentId);
        lenient().when(tweetAndCounts.getParentId()).thenReturn(tweetId);
        lenient().when(tweetAndCounts.getContent()).thenReturn("comment");
        lenient().when(tweetAndCounts.getCreatedAt()).thenReturn(LocalDateTime.now());
        lenient().when(tweetAndCounts.getViewsCount()).thenReturn(0L);
        lenient().when(tweetAndCounts.getLikesCount()).thenReturn(0L);
        lenient().when(tweetAndCounts.getCommentsCount()).thenReturn(0L);

        com.api.twitter.user.domain.UserProfile userProfile = mock(com.api.twitter.user.domain.UserProfile.class);
        lenient().when(userProfile.getUserId()).thenReturn(userId);
        lenient().when(userProfile.getUsername()).thenReturn("username");
        lenient().when(tweetAndCounts.getUser()).thenReturn(userProfile);

        Page<TweetAndCounts> page = new PageImpl<>(List.of(tweetAndCounts), pageable, 1);
        TweetResponse tweetResponse = new TweetResponse(
                commentId, tweetId, "comment", LocalDateTime.now(), 0L, 0L, 0L,
                new TweetUserResponse(userId, "username"));

        when(tweetRepository.findAllTweetAndCountsByParentId(tweetId, pageable)).thenReturn(page);
        when(tweetMapper.toResponse(any(TweetAndCounts.class))).thenReturn(tweetResponse);

        // When
        PagedResponse<TweetResponse> result = listTweetsUseCase.listAllCommentsByTweetId(tweetId, pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.content().size());
        verify(tweetRepository).findAllTweetAndCountsByParentId(tweetId, pageable);
    }
}