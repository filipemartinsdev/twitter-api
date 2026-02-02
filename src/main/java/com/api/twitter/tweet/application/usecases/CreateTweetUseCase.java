package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.application.dto.TweetRequest;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.user.application.usecases.GetUserEntityStrictUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class CreateTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private GetUserEntityStrictUseCase getUserEntityStrictUseCase;

    @Autowired
    private TweetMapper tweetMapper;

    @Transactional
    public TweetResponse execute(UUID userId, TweetRequest tweetRequest) {
        Tweet tweet = Tweet.newDefault();
        tweet.setUser(getUserEntityStrictUseCase.execute(userId));
        tweet.setContent(tweetRequest.content());
        tweet.setParentId(tweetRequest.parentId());

        if (tweetRequest.parentId() != null)
            incrementParentTweetCommentsCount(tweetRequest.parentId());

        Tweet savedTweet = tweetRepository.save(tweet);
        return tweetMapper.toResponse(savedTweet);
    }

    private void incrementParentTweetCommentsCount(UUID parentTweetId){
        Tweet parentTweet = tweetRepository.findById(parentTweetId)
                .orElseThrow(() -> new NotFoundException("Parent Tweet not found"));

        parentTweet.incrementCommentsCount();
        tweetRepository.save(parentTweet);
    }
}
