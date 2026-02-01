package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.domain.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    @Query(
            "SELECT t FROM Tweet t "+
            "WHERE t.parentId is null "+
            "AND t.user.id = :userId"
    )
    Page<Tweet> findAllTweetsByUserId(@Param("userId") UUID userId, Pageable pageable);

    Page<Tweet> findAllByParentId(UUID parentId, Pageable pageable);

    @Query(
            "SELECT t FROM Tweet t "+
            "WHERE t.parentId is null"
    )
    Page<Tweet> findAllTweets(Pageable pageable);

    void deleteAllByUserId(UUID userId);
}
