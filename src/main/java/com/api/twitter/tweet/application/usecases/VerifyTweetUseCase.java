package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerifyTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    public boolean isTweetFromUser(UUID tweetId, UUID userId){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        return tweet.getUser().getId().compareTo(userId) == 0;
    }
}
