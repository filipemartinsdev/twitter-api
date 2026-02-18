package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetLikeRepository;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DeleteTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Transactional
    @CacheEvict(value = "tweet", allEntries = true)
    @Caching(evict = {
            @CacheEvict(value = "tweetById", key = "#tweetId.toString()"),
            @CacheEvict(value = "tweetPages", allEntries = true)
    })
    public void execute(UUID tweetId){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        if (tweet.getUser().getId().compareTo(authenticatedUserId) != 0)
            throw new UnauthorizedException("The authenticated user is not owner of this tweet");

        tweetRepository.deleteById(tweetId);

        UUID parentId = tweet.getParentId();
    }
}
