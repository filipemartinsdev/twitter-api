package com.api.twitter.tweet.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.tweet.application.dto.TweetRequest;
import com.api.twitter.tweet.application.dto.TweetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "Tweet")
public interface CreateTweetControllerDocs {
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tweet created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_RESPONSE)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Tweet can't be blank",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_CANT_BE_BLANK)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            ),
    })
    ResponseEntity<ApiResponseDTO<TweetResponse>> createTweet(TweetRequest tweetRequest);
}
