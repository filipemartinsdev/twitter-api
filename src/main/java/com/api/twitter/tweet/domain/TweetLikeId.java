package com.api.twitter.tweet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class TweetLikeId implements Serializable {
    @Column(name = "tweet_id")
    private UUID tweetId;

    @Column(name = "user_id")
    private UUID userId;
}
