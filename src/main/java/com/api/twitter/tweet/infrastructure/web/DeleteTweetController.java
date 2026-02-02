package com.api.twitter.tweet.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.tweet.application.usecases.DeleteTweetUseCase;
import com.api.twitter.tweet.application.usecases.VerifyTweetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/tweets")
public class DeleteTweetController {
    @Autowired
    private DeleteTweetUseCase deleteTweetUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private VerifyTweetUseCase verifyTweetUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTweetById(@PathVariable UUID id){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        if (!verifyTweetUseCase.isTweetFromUser(id, authenticatedUserId))
            throw new UnauthorizedException("Authenticated user is not owner of this tweet");

        deleteTweetUseCase.execute(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success());
    }
}
