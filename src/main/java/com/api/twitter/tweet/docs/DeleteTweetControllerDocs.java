package com.api.twitter.tweet.docs;

import com.api.twitter.common.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Tweet")
public interface DeleteTweetControllerDocs {
    @SecurityRequirement(name = "bearerAuth")
    @Operation(

    )
    @ApiResponses(value = {

    })
    ResponseEntity<ApiResponseDTO<Void>> deleteTweetById(UUID id);
}
