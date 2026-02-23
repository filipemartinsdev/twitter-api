package com.api.twitter.user.relationship.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.relationship.application.usecases.FollowUserUseCase;
import com.api.twitter.user.relationship.application.usecases.UnfollowUserUseCase;
import com.api.twitter.user.relationship.docs.ManageRelationshipControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ManageRelationshipController implements ManageRelationshipControllerDocs {
    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private FollowUserUseCase followUserUseCase;

    @Autowired
    private UnfollowUserUseCase unfollowUserUseCase;

    @PostMapping("/{userId}/followers")
    public ResponseEntity<ApiResponseDTO<Void>> followUser(@PathVariable UUID userId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();
        followUserUseCase.execute(authenticatedUserId, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success());
    }

    @DeleteMapping("/{userId}/followers")
    public ResponseEntity<ApiResponseDTO<Void>> unfollowUser(@PathVariable UUID userId){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();
        unfollowUserUseCase.execute(authenticatedUserId, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.success());
    }
}