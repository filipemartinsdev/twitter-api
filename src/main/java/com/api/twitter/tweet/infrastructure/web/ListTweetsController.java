package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.usecases.ListTweetsUseCase;
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
public class ListTweetsController {
    @Autowired
    private ListTweetsUseCase listTweetsUseCase;

    @GetMapping("/tweets/{tweetId}")
    private ResponseEntity<ApiResponse<TweetResponse>> getTweetById(@PathVariable UUID tweetId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listTweetsUseCase.getById(tweetId)
                ));
    }

    @GetMapping("/tweets")
    private ResponseEntity<ApiResponse<PagedResponse<TweetResponse>>> getAllTweets(Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listTweetsUseCase.listAll(pageable)
                ));
    }

    @GetMapping("/tweets/{tweetId}/comments")
    private ResponseEntity<ApiResponse<PagedResponse<TweetResponse>>> getAllCommentsOfTweet(@PathVariable UUID tweetId, Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listTweetsUseCase.listAllCommentsByTweetId(tweetId, pageable)
                ));
    }

    @GetMapping("/users/{userId}/tweets")
    private ResponseEntity<ApiResponse<PagedResponse<TweetResponse>>> getAllTweetsOfUser(@PathVariable UUID userId, Pageable pageable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listTweetsUseCase.listAllTweetsByUserId(userId, pageable)
                ));
    }
}
