package com.api.twitter.user.infrastructure.web;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.common.events.UserProfileUpdatedEvent;
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase;
import com.api.twitter.user.application.dto.UserProfileResponse;
import com.api.twitter.user.application.dto.UserUpdateRequest;
import com.api.twitter.user.application.usecases.UpdateUserUseCase;
import com.api.twitter.user.docs.UpdateUserControllerDocs;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
public class UpdateUserController implements UpdateUserControllerDocs {
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private ApplicationEventPublisher eventPublisher;

    public UpdateUserController(
            UpdateUserUseCase updateUserUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            ApplicationEventPublisher eventPublisher
    ){
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.eventPublisher = eventPublisher;
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserProfileResponse>> updateUser(@Valid @RequestBody UserUpdateRequest request){
        var authUser = getAuthenticatedUserUseCase.execute()
                .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));
        var updatedUserProfile = updateUserUseCase.updateUser(authUser.id(), request);

        eventPublisher.publishEvent(new UserProfileUpdatedEvent(
                updatedUserProfile.userId(),
                updatedUserProfile.username(),
                updatedUserProfile.email(),
                this
        ));

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(ApiResponseDTO.success(updatedUserProfile));
    }
}