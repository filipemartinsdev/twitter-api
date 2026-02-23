package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.usecases.ListTweetsUseCase;
import com.api.twitter.tweet.docs.ListTweetsControllerDocs;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2")
public class ListTweetsController implements ListTweetsControllerDocs {
    @Autowired
    private ListTweetsUseCase listTweetsUseCase;

    @GetMapping("/tweets/{tweetId}")
    public ResponseEntity<ApiResponseDTO<TweetResponse>> getTweetById(@PathVariable UUID tweetId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listTweetsUseCase.getById(tweetId)
                ));
    }

    @GetMapping("/tweets")
    public ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllTweets(Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listTweetsUseCase.listAll(pageable)
                ));
    }

    @GetMapping("/tweets/{tweetId}/comments")
    public ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllCommentsOfTweet(@PathVariable UUID tweetId, Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listTweetsUseCase.listAllCommentsByTweetId(tweetId, pageable)
                ));
    }

    @GetMapping("/users/{userId}/tweets")
    public ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllTweetsOfUser(@PathVariable UUID userId, Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listTweetsUseCase.listAllTweetsByUserId(userId, pageable)
                ));
    }
}
