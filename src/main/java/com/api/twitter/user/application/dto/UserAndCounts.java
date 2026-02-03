package com.api.twitter.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public interface UserAndCounts {
    UUID getId();
    String getUsername();
    String getEmail();
    Long getTweetsCount();
    Long getFollowersCount();
    Long getFollowingCount();
}

