package com.api.twitter.security.application.handler

import com.api.twitter.common.dto.ApiResponseDTO
import com.api.twitter.security.application.exception.ExpiredTokenException
import com.api.twitter.security.application.exception.InvalidTokenException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SecurityExceptionHandler {
    @ExceptionHandler(ExpiredTokenException::class)
    fun expiredTokenHandler(exception: ExpiredTokenException): ResponseEntity<ApiResponseDTO<Void>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseDTO.fail(exception.message))
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun invalidTokenHandler(exception: InvalidTokenException): ResponseEntity<ApiResponseDTO<Void>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseDTO.fail(exception.message))
    }
}
