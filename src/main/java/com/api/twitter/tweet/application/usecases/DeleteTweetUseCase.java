package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetLikeRepository;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void execute(UUID tweetId){
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new NotFoundException("Tweet not found"));

        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        if (tweet.getUser().getId().compareTo(authenticatedUserId) != 0)
            throw new UnauthorizedException("The authenticated user is not owner of this tweet");

        tweetRepository.deleteById(tweetId);

        UUID parentId = tweet.getParentId();
        if (parentId != null){
            decrementParentTweetCommentsCount(parentId);
        }
    }

    private void decrementParentTweetCommentsCount(UUID parentTweetId){
        Tweet parentTweet = tweetRepository.findById(parentTweetId)
                .orElseThrow(() -> new NotFoundException("Parent Tweet not found"));
        parentTweet.decrementCommentsCount();
        tweetRepository.save(parentTweet);
    }
}
