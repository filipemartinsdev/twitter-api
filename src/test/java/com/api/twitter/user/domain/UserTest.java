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
    private UserProfile userMock1 = new UserProfile(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            null,
//            UserRole.USER,
            LocalDateTime.now()
    );

    private UserProfile userInvalidMock1 = new UserProfile(
            UUID.randomUUID(),
            "",
            "$%",
            null,
//            UserRole.USER,
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
}