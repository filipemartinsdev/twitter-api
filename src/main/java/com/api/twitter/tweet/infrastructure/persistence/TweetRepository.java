package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.domain.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
}
