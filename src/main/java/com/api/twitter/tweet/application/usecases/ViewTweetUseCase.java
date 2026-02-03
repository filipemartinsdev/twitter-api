package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ViewTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    public void viewTweetAsUser(UUID tweetId, UUID userId){
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        if (tweet.getUser().getId().compareTo(userId) == 0)
            throw new BadRequestException("Can't view your self tweet");

        tweet.incrementViewsCount();
        tweetRepository.save(tweet);
    }
}
