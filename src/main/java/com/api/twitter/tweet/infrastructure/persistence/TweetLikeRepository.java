package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.domain.TweetLike;
import com.api.twitter.tweet.domain.TweetLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetLikeRepository extends JpaRepository<TweetLike, TweetLikeId> {
}
