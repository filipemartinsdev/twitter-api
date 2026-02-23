package com.api.twitter.user.docs;

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

@Tag(name = "User")
public interface DeleteUserControllerDocs {

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete authenticated user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(value = ResponseExamples.SUCCESS)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> deleteAuthenticatedUser();
}
