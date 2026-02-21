package com.api.twitter.tweet.application.dto;

import com.api.twitter.user.domain.UserProfile;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TweetAndCounts {
    UUID getId();
    UUID getParentId();
    String getContent();
    LocalDateTime getCreatedAt();
    UserProfile getUser();
    Long getViewsCount();
    Long getLikesCount();
    Long getCommentsCount();
}
