package com.api.twitter.user.relationship.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.docs.ResponseExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "User Relationship")
public interface ListUserRelationshipsControllerDocs {

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get all following of a user",
            description = "Returns a paginated list of users that the given user ID is following"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved users",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.PAGED_USER_RESPONSE)
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
    ResponseEntity<ApiResponseDTO<PagedResponse<UserResponse>>> getFollowingOfUser(
            UUID userId,
            Pageable pageable
    );

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get all followers of a user",
            description = "Returns a paginated list of followers for a given user ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved followers",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.PAGED_USER_RESPONSE)
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
    ResponseEntity<ApiResponseDTO<PagedResponse<UserResponse>>> getFollowersOfUser(
            UUID userId,
            Pageable pageable
    );
}
