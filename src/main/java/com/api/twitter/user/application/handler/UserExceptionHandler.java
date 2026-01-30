package com.api.twitter.user.application.handler;

import com.api.twitter.common.dto.ApiResponse;
import com.api.twitter.user.application.exception.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ApiResponse<Void>> userValidationHandler(UserValidationException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(exception.getMessage()));
    }
}
