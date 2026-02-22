package com.api.twitter.user.application.handler;

import com.api.twitter.common.dto.ApiResponseDTO;
import com.api.twitter.user.application.exception.UserNotExistsException;
import com.api.twitter.user.application.exception.UserValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ApiResponseDTO<Void>> userValidationHandler(UserValidationException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.fail(exception.getMessage()));
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ApiResponseDTO<Void>> userNotExistsHandler(UserNotExistsException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDTO.fail(exception.getMessage()));
    }
}
