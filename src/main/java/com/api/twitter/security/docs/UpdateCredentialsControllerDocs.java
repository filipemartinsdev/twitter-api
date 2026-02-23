package com.api.twitter.security.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.security.application.dto.UpdatePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication")
public interface UpdateCredentialsControllerDocs {

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Update user password"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Password updated successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Invalid password",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = """
                                            {
                                                "status": "fail",
                                                "message": "Invalid password"
                                            }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - User not authenticated",
                    content = @Content
            )
    })
    ResponseEntity<ApiResponseDTO<Void>> updatePassword(@Valid @RequestBody UpdatePasswordRequest request);
}
