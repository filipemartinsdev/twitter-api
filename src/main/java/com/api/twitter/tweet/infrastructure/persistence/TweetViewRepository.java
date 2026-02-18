package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.domain.TweetView;
import com.api.twitter.tweet.domain.TweetViewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetViewRepository extends JpaRepository<TweetView, TweetViewId> {
}
