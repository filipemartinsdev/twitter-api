package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.application.usecases.InteractTweetUseCase;
import com.api.twitter.tweet.application.usecases.ViewTweetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/tweets")
public class InteractTweetController {
    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private InteractTweetUseCase interactTweetUseCase;

    @Autowired
    private ViewTweetUseCase viewTweetUseCase;

    @PostMapping("/likes/{tweetId}")
    public ResponseEntity<ApiResponse<Void>> likeTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        interactTweetUseCase.likeTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/likes/{tweetId}")
    public ResponseEntity<ApiResponse<Void>> unlikeTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        interactTweetUseCase.unlikeTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/views/{tweetId}")
    public ResponseEntity<ApiResponse<Void>> viewTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        viewTweetUseCase.viewTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
