package com.api.twitter.security.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.dto.AuthenticatedUser;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.application.dto.UpdatePasswordRequest;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.security.application.usecases.UpdateUserCredentialsUseCase;
import com.api.twitter.security.docs.UpdateCredentialsControllerDocs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth/users")
public class UpdateCredentialsController implements UpdateCredentialsControllerDocs {
    private UpdateUserCredentialsUseCase updateUserCredentialsUseCase;
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    public UpdateCredentialsController(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase, UpdateUserCredentialsUseCase updateUserCredentialsUseCase) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.updateUserCredentialsUseCase = updateUserCredentialsUseCase;
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponseDTO<Void>> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
         AuthenticatedUser userAuth = getAuthenticatedUserUseCase.execute()
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));

        updateUserCredentialsUseCase.updatePassword(userAuth.id(), request.password());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success());
    }
}
