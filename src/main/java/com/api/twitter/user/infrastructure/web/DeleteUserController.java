package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.usecases.DeleteUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class DeleteUserController {
    @Autowired
    private DeleteUserUseCase deleteUserUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteAuthenticatedUser(){
        UUID authenticatedUserId = getAuthenticatedUserUseCase.execute().id();
        deleteUserUseCase.deleteById(authenticatedUserId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success());
    }
}
