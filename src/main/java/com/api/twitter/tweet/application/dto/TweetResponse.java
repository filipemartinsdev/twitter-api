package com.api.twitter.tweet.application.dto;

import com.api.twitter.user.application.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record TweetResponse (
        UUID id,
        UUID parentId,
        String content,
        LocalDateTime createdAt,
        Long viewsCount,
        Long likesCount,
        Long commentsCount,
        TweetUserResponse user
){}
