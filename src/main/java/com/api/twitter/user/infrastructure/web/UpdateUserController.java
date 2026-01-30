package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.usecases.UpdateUserUseCase;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/users")
public class UpdateUserController {
    @Autowired
    private VerifyUserUseCase verifyUserUseCase;

    @Autowired
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @RequestBody Map<String, Object> fields
    ){
        UUID authenticatedUserID = getAuthenticatedUserUseCase.execute().id();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(updateUserUseCase.execute(authenticatedUserID, fields)));
    }
}