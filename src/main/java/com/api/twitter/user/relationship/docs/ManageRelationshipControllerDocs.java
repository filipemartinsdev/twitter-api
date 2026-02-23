package com.api.twitter.user.relationship.docs;

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

@Tag(name = "User Relationship")
public interface ManageRelationshipControllerDocs {

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Follow a user")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Successfully followed the user",
                content = @Content(
                        schema = @Schema(implementation = ApiResponseDTO.class),
                        examples = {
                                @ExampleObject(value = ResponseExamples.FOLLOW_USER_SUCCESS)
                        }
                )
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Its already following this user",
                content = @Content(
                        schema = @Schema(implementation = ApiResponseDTO.class),
                        examples = {
                                @ExampleObject(value = ResponseExamples.ALREADY_FOLLOWING)
                        }
                )
        ),
        @ApiResponse(
                responseCode = "403",
                description = "User not authenticated",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = @Content(
                        schema = @Schema(implementation = ApiResponseDTO.class),
                        examples = {
                                @ExampleObject(value = ResponseExamples.USER_NOT_FOUND)
                        }
                )
        )
    })
    ResponseEntity<ApiResponseDTO<Void>> followUser(UUID userId);

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Unfollow a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully unfollowed the user",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Its not following this user yet",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.NOT_FOLLOWING_YET)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.USER_NOT_FOUND)
                            }
                    )
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> unfollowUser(UUID userId);
}
