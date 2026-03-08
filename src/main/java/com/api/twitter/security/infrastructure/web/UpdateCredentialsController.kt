package com.api.twitter.security.infrastructure.web

import com.api.twitter.common.dto.ApiResponseDTO
import com.api.twitter.common.dto.AuthenticatedUser
import com.api.twitter.security.application.dto.UpdatePasswordRequest
import com.api.twitter.security.application.usecases.GetAuthenticatedUserUseCase
import com.api.twitter.security.application.usecases.UpdateUserCredentialsUseCase
import com.api.twitter.security.docs.UpdateCredentialsControllerDocs
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/auth/users")
class UpdateCredentialsController(
    private val getAuthenticatedUserUseCase: GetAuthenticatedUserUseCase,
    private val updateUserCredentialsUseCase: UpdateUserCredentialsUseCase
) : UpdateCredentialsControllerDocs {
    @PutMapping("/me/password")
    override fun updatePassword(@Valid @RequestBody request: @Valid UpdatePasswordRequest): ResponseEntity<ApiResponseDTO<Void>> {
        val userAuth: AuthenticatedUser = getAuthenticatedUserUseCase.execute()
        updateUserCredentialsUseCase.updatePassword(userAuth.id, request.password)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO.success())
    }
}
