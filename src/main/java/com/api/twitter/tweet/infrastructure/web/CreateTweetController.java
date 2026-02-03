package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.application.dto.TweetRequest;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.usecases.CreateTweetUseCase;
import com.api.twitter.tweet.application.usecases.VerifyTweetUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/tweets")
public class CreateTweetController {
    @Autowired
    private CreateTweetUseCase createTweetUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<TweetResponse>> createTweet(@Valid @RequestBody TweetRequest tweetRequest){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        TweetResponse createdTweet = createTweetUseCase.execute(authenticatedUserId, tweetRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdTweet));
    }
}
