package com.api.twitter.user.relationship.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.relationship.application.usecases.ListRelationshipsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ListUserRelationshipsController {
    @Autowired
    private ListRelationshipsUseCase listRelationshipsUseCase;

    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getFollowersOfUser(
            @PathVariable UUID userId, Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listRelationshipsUseCase.getAllFollowersOfUser(userId, pageable)
                ));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getFollowingOfUser(
            @PathVariable UUID userId,
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        listRelationshipsUseCase.getAllFollowingOfUser(userId, pageable)
                ));
    }
}
