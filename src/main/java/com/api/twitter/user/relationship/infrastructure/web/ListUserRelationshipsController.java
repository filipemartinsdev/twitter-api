package com.api.twitter.user.relationship.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.relationship.application.usecases.ListRelationshipsUseCase;
import com.api.twitter.user.relationship.docs.ListUserRelationshipsControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ListUserRelationshipsController implements ListUserRelationshipsControllerDocs {
    @Autowired
    private ListRelationshipsUseCase listRelationshipsUseCase;

    @GetMapping("/{userId}/followers")
    public ResponseEntity<ApiResponseDTO<PagedResponse<UserResponse>>> getFollowersOfUser(
            @PathVariable UUID userId, Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listRelationshipsUseCase.getAllFollowersOfUser(userId, pageable)
                ));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponseDTO<PagedResponse<UserResponse>>> getFollowingOfUser(
            @PathVariable UUID userId,
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.success(
                        listRelationshipsUseCase.getAllFollowingOfUser(userId, pageable)
                ));
    }
}
