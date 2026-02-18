package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.application.dto.TweetRequest;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import com.api.twitter.user.application.usecases.GetUserEntityStrictUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @CacheEvict(value = "tweetPages", allEntries = true)
    public TweetResponse execute(UUID userId, TweetRequest tweetRequest) {
        Tweet tweet = new Tweet();

        tweet.setParentId(tweetRequest.parentId());
        tweet.setUser(getUserEntityStrictUseCase.execute(userId));
        tweet.setContent(tweetRequest.content());
        tweet.setCreatedAt(LocalDateTime.now());

        Tweet savedTweet = tweetRepository.save(tweet);
        return tweetMapper.toResponse(savedTweet);
    }
}
