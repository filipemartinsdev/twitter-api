package com.api.twitter.tweet.infrastructure.persistence;

import com.api.twitter.tweet.domain.TweetLike;
import com.api.twitter.tweet.domain.TweetLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TweetLikeRepository extends JpaRepository<TweetLike, TweetLikeId> {
    void deleteAllByTweetOwnerId(UUID tweetOwnerId);

    @Modifying
    @Transactional
    @Query(
            "DELETE FROM TweetLike tl "+
            "WHERE tl.id.userId = :userId"
    )
    void deleteAllByUserId(@Param("userId") UUID userId);

    /**
     * The database is configured with constraint ON DELETE CASCADE
     * @param tweetId
     */
    @Deprecated
    @Modifying
    @Transactional
    @Query(
            "DELETE FROM TweetLike tl "+
            "WHERE tl.id.tweetId = :tweetId"
    )
    void deleteAllByTweetId(@Param("tweetId") UUID tweetId);
}
