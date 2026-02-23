package com.api.twitter.tweet.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Tweet")
public interface InteractTweetControllerDocs {
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Like a tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tweet liked successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tweet not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_NOT_FOUND)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User has already liked this tweet",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.USER_HAS_ALREADY_LIKED)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> likeTweet(UUID tweetId);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Unlike a tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tweet unliked successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tweet not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_NOT_FOUND)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User hasn't liked this tweet yet",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.USER_HASNT_LIKED)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> unlikeTweet(@PathVariable UUID tweetId);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "View a tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tweet viewed successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tweet not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.TWEET_NOT_FOUND)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User has already viewed this tweet",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.USER_HAS_ALREADY_VIEWED)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Can't view your self tweet",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.CANT_VIEW_YOURSELF)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> viewTweet(@PathVariable UUID tweetId);
}
