package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.domain.TweetLike;
import com.api.twitter.tweet.domain.TweetLikeId;
import com.api.twitter.tweet.infrastructure.persistence.TweetLikeRepository;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InteractTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetLikeRepository tweetLikeRepository;

    @Transactional
    public void likeTweetAsUser(UUID tweetId, UUID userId){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        var tweetLikeId = new TweetLikeId(tweetId, userId);

        if (tweetLikeRepository.existsById(tweetLikeId))
            throw new BadRequestException("User has already liked this tweet");

        if (tweet.getUser().getId().compareTo(userId) == 0)
            throw new BadRequestException("Can't like your self tweet");

        TweetLike tweetLike = new TweetLike(
                tweetLikeId,
                LocalDateTime.now(),
                tweet.getUser().getId()
        );

        tweet.incrementLikesCount();

        tweetRepository.save(tweet);
        tweetLikeRepository.save(tweetLike);
    }

    @Transactional
    public void unlikeTweetAsUser(UUID tweetId, UUID userId){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        var tweetLikeId = new TweetLikeId(tweetId, userId);

        if (!tweetLikeRepository.existsById(tweetLikeId))
            throw new BadRequestException("User hasn't liked this tweet yet");

        if (tweet.getUser().getId().compareTo(userId) == 0)
            throw new BadRequestException("Can't unlike your self tweet");

        tweet.decrementLikesCount();
        tweetRepository.save(tweet);

        tweetLikeRepository.deleteById(tweetLikeId);
    }


}
