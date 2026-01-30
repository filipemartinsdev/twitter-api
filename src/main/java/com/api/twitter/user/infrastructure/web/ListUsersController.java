package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.usecases.ListUsersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ListUsersController {
    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(
            @RequestParam(value = "q") Optional<String> query,
            Pageable pageable
    ){
        PagedResponse<UserResponse> pagedUserResponse;

        if(query.isPresent()){
            pagedUserResponse = listUsersUseCase.query(query.get(), pageable);
        }
        else {
            pagedUserResponse = listUsersUseCase.getAll(pageable);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.success(pagedUserResponse)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.success(listUsersUseCase.getById(id))
                );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getAuthenticatedUser(){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.success(listUsersUseCase.getById(authenticatedUserId))
                );
    }
}
