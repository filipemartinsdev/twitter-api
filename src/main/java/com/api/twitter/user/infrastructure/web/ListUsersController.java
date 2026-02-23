package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.usecases.ListUsersUseCase;
import com.api.twitter.user.docs.ListUsersControllerDocs;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ListUsersController implements ListUsersControllerDocs {
    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<PagedResponse<UserResponse>>> getAllUsers(
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
                        ApiResponseDTO.success(pagedUserResponse)
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<UserResponse>> getUserById(@PathVariable @Valid UUID userId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponseDTO.success(listUsersUseCase.getById(userId))
                );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponse>> getAuthenticatedUser(){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.getId();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponseDTO.success(listUsersUseCase.getById(authenticatedUserId))
                );
    }
}
