package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteTweetUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private VerifyTweetUseCase verifyTweetUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    public void execute(UUID tweetId){
        if (!tweetRepository.existsById(tweetId))
            throw new NotFoundException("Tweet not found");

        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        if (!verifyTweetUseCase.isTweetFromUser(tweetId, authenticatedUserId))
            throw new UnauthorizedException("The authenticated user is not owner of this tweet");

        tweetRepository.deleteById(tweetId);
    }

    public void deleteAllOfUser(UUID userId){
        tweetRepository.deleteAllByUserId(userId);
    }
}
