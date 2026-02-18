package com.api.twitter.tweet.application.dto;

import com.api.twitter.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TweetAndCounts {
    UUID getId();
    UUID getParentId();
    String getContent();
    LocalDateTime getCreatedAt();
    User getUser();
    Long getViewsCount();
    Long getLikesCount();
    Long getCommentsCount();
}
