package com.api.twitter.user.relationship.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class CreateRelationshipController {
    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @PostMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> followUser(@PathVariable UUID id){
        return null;
    }
}



