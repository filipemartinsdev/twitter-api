package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.application.usecases.InteractTweetUseCase;
import com.api.twitter.tweet.application.usecases.ViewTweetUseCase;
import com.api.twitter.tweet.docs.InteractTweetControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/tweets")
public class InteractTweetController implements InteractTweetControllerDocs {
    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private InteractTweetUseCase interactTweetUseCase;

    @Autowired
    private ViewTweetUseCase viewTweetUseCase;

    @PostMapping("/{tweetId}/likes")
    public ResponseEntity<ApiResponseDTO<Void>> likeTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();
        interactTweetUseCase.likeTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{tweetId}/likes")
    public ResponseEntity<ApiResponseDTO<Void>> unlikeTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();
        interactTweetUseCase.unlikeTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("{tweetId}/views")
    public ResponseEntity<ApiResponseDTO<Void>> viewTweet(@PathVariable UUID tweetId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();
        viewTweetUseCase.viewTweetAsUser(tweetId, authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
