package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.usecases.ListUsersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class ListUsersController {
    @Autowired
    private ListUsersUseCase listUsersUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.success(listUsersUseCase.getAll())
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
}
