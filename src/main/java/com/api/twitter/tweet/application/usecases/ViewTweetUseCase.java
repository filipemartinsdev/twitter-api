package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.application.exceptions.TweetNotFound;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.domain.TweetView;
import com.api.twitter.tweet.domain.TweetViewId;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.tweet.infrastructure.persistence.TweetViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ViewTweetUseCase {
    @Autowired
    private TweetViewRepository tweetViewRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @CacheEvict(value = "tweetPages", allEntries = true)
    public void viewTweetAsUser(UUID tweetId, UUID userId){
        TweetViewId tweetViewId = new TweetViewId(tweetId, userId);

        if (tweetViewRepository.existsById(tweetViewId))
            throw new BadRequestException("Already viewed this tweet");

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFound("Tweet not exists"));

        if (tweet.getUser().getId().compareTo(userId) == 0)
            throw new BadRequestException("Can't view your self tweet");

        tweetViewRepository.save(
                new TweetView(tweetViewId, LocalDateTime.now())
        );
    }
}
