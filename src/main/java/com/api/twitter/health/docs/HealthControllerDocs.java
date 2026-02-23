package com.api.twitter.health.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Health")
public interface HealthControllerDocs {
    @Operation (
            summary = "Health Check"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API is healthy",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "API is unhealthy",
                    content = @Content
            )
    })
    public ResponseEntity<Map<String, String>> healthCheck();
}
