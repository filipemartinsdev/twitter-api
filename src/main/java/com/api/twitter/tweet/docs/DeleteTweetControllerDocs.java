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

import java.util.UUID;

@Tag(name = "Tweet")
public interface DeleteTweetControllerDocs {
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete tweet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tweet deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.SUCCESS)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not the owner of the tweet",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> deleteTweetById(UUID id);
}
