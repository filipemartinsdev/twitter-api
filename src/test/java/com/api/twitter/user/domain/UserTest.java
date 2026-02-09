package com.api.twitter.user.domain;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.exception.UserValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UserTest {
    private User userMock1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "12345",
            UserRole.USER,
            LocalDateTime.now()
    );

    private User userInvalidMock1 = new User(
            UUID.randomUUID(),
            "",
            "$%",
            " ",
            UserRole.USER,
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should validate successfully if username is valid")
    public void validateUsernameTestCase1(){
        userMock1.validateUsername();
    }

    @Test
    @DisplayName("Should throw UserValidationException if username is invalid")
    public void validateUsernameTestCase2(){
        assertThrows(UserValidationException.class, () -> {
            userInvalidMock1.validateUsername();
        });
    }

    @Test
    @DisplayName("Should validate successfully if email is valid")
    public void validateEmailTestCase1(){
        userMock1.validateEmail();
    }

//    @Test
//    @DisplayName("Should throw UserValidationException if email is invalid")
//    public void validateEmailTestCase2(){
//        assertThrows(UserValidationException.class, () -> {
//            userInvalidMock1.validateEmail();
//        });
//    }

    @Test
    @DisplayName("Should validate successfully if password is valid")
    public void validatePasswordTestCase1(){
        userMock1.validatePassword();
    }

    @Test
    @DisplayName("Should throw UserValidationException if password is invalid")
    public void validatePasswordTestCase2(){
        assertThrows(UserValidationException.class, () -> {
            userInvalidMock1.validatePassword();
        });
    }
}