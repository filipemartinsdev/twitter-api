package com.api.twitter.user.relationship.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.relationship.application.usecases.FollowUserUseCase;
import com.api.twitter.user.relationship.application.usecases.UnfollowUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ManageRelationshipController {
    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private FollowUserUseCase followUserUseCase;

    @Autowired
    private UnfollowUserUseCase unfollowUserUseCase;

    @PostMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<Void>> followUser(@PathVariable UUID id){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        followUserUseCase.execute(authenticatedUserId, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success());
    }

    @DeleteMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<Void>> unfollowUser(@PathVariable UUID id){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        unfollowUserUseCase.execute(authenticatedUserId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success());
    }
}