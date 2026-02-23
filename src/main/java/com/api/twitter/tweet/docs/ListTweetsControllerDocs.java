package com.api.twitter.tweet.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.tweet.application.dto.TweetResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "Tweet")
public interface ListTweetsControllerDocs {
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get tweet by ID")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Tweet retrieved successfully",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ApiResponseDTO.class),
                        examples = {
                                @ExampleObject(value = ResponseExamples.TWEET_RESPONSE)
                        }
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Tweet not found",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ApiResponseDTO.class),
                        examples = {
                                @ExampleObject(value = ResponseExamples.TWEET_NOT_FOUND)
                        }
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User not authenticated",
                content = @Content
        )
    })
    ResponseEntity<ApiResponseDTO<TweetResponse>> getTweetById(UUID tweetId);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all tweets")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tweets retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.PAGED_TWEETS)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    @PageableAsQueryParam
    ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllTweets(@Parameter(hidden = true) Pageable pageable);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all comments of tweet by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.PAGED_COMMENTS)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tweet not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_NOT_FOUND)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllCommentsOfTweet(UUID tweetId, Pageable pageable);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all tweets of user by userId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tweets retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.PAGED_TWEETS)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.USER_NOT_FOUND)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<PagedResponse<TweetResponse>>> getAllTweetsOfUser(UUID userId, Pageable pageable);
}
