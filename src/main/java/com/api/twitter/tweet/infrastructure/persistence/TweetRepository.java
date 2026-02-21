package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.application.dto.TweetAndCounts;
import com.api.twitter.tweet.domain.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TweetRepository extends JpaRepository<Tweet, UUID> {
    @Deprecated
    @Query(
            "SELECT t FROM Tweet t "+
            "WHERE t.parentId is null "+
            "AND t.user.userId = :userId"
    )
    Page<Tweet> findAllTweetsByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Deprecated
    Page<Tweet> findAllByParentId(UUID parentId, Pageable pageable);

    @Deprecated
    @Query(
            "SELECT t FROM Tweet t "+
            "WHERE t.parentId is null"
    )
    Page<Tweet> findAllTweets(Pageable pageable);

    @Query(
            """
            SELECT
            t.id AS id,
            t.user.userId AS userId,
            t.parentId AS parentId,
            t.content AS content,
            t.createdAt AS createdAt,
            t.user AS user,
            COUNT(DISTINCT v) AS viewsCount,
            COUNT(DISTINCT l) AS likesCount,
            COUNT(DISTINCT c) AS commentsCount
            FROM Tweet t
            
            LEFT JOIN TweetView v ON v.id.tweetId = t.user.userId
            LEFT JOIN TweetLike l ON l.id.tweetId = t.user.userId
            LEFT JOIN Tweet c ON c.parentId = t.user.userId
            
            WHERE t.user.userId = :tweetId
            GROUP BY t.id, t.user
            """
    )
    Optional<TweetAndCounts> findTweetAndCountsById(@Param("tweetId") UUID id);

    @Query(
            """
            SELECT 
            t.id AS id,
            t.user.userId AS userId,
            t.parentId AS parentId, 
            t.content AS content, 
            t.createdAt AS createdAt, 
            t.user AS user,
            COUNT(DISTINCT v) AS viewsCount,
            COUNT(DISTINCT l) AS likesCount,
            COUNT(DISTINCT c) AS commentsCount
            FROM Tweet t
            
            LEFT JOIN TweetView v ON v.id.tweetId = t.user.userId
            LEFT JOIN TweetLike l ON l.id.tweetId = t.user.userId
            LEFT JOIN Tweet c ON c.parentId = t.user.userId
            
            WHERE t.parentId IS NULL
            GROUP BY t.id, t.user
            """
    )
    Page<TweetAndCounts> findAllTweetAndCounts(Pageable pageable);

    @Query(
            """
            SELECT
            t.id AS id,
            t.user.userId AS userId,
            t.parentId AS parentId, 
            t.content AS content, 
            t.createdAt AS createdAt, 
            t.user AS user,
            COUNT(DISTINCT v) AS viewsCount,
            COUNT(DISTINCT l) AS likesCount,
            COUNT(DISTINCT c) AS commentsCount
            FROM Tweet t
            
            LEFT JOIN TweetView v ON v.id.tweetId = t.user.userId
            LEFT JOIN TweetLike l ON l.id.tweetId = t.user.userId
            LEFT JOIN Tweet c ON c.parentId = t.user.userId
            
            WHERE t.parentId IS NULL
            AND t.user.userId = :userId
            GROUP BY t.id, t.user
            """
    )
    Page<TweetAndCounts> findAllTweetAndCountsByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query(
            """
            SELECT
            t.id AS id,
            t.user.userId AS userId,
            t.parentId AS parentId, 
            t.content AS content, 
            t.createdAt AS createdAt, 
            t.user AS user,
            COUNT(DISTINCT v) AS viewsCount,
            COUNT(DISTINCT l) AS likesCount,
            COUNT(DISTINCT c) AS commentsCount
            FROM Tweet t
            
            LEFT JOIN TweetView v ON v.id.tweetId = t.user.userId
            LEFT JOIN TweetLike l ON l.id.tweetId = t.user.userId
            LEFT JOIN Tweet c ON c.parentId = t.user.userId
            
            WHERE t.parentId = :parentId
            GROUP BY t.id, t.user
            """
    )
    Page<TweetAndCounts> findAllTweetAndCountsByParentId(@Param("parentId") UUID parentId, Pageable pageable);
}
