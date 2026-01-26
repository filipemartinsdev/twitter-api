package com.api.twitter.tweet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name = "tweet_like")
@AllArgsConstructor @NoArgsConstructor
public class TweetLike {
    @EmbeddedId
    private TweetLikeId id;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
