package com.api.twitter.security.application.usecases;

import com.api.twitter.common.dto.AuthenticatedUser;
import com.api.twitter.common.exception.UnauthorizedException;
import com.api.twitter.security.domain.model.UserCredentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAuthenticatedUserUseCaseTests {
    @InjectMocks private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    @Test @DisplayName("Should return authenticated user when user is logged in")
    void executeTestCase1() {
        // Given
        UUID userId = UUID.randomUUID();
        String username = "username";

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userCredentials, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        Optional<AuthenticatedUser> result = getAuthenticatedUserUseCase.execute();

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().id());
        assertEquals(username, result.get().username());
    }

    @Test @DisplayName("Should return empty when user is not authenticated")
    void executeTestCase2() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(null);

        // When
        Optional<AuthenticatedUser> result = getAuthenticatedUserUseCase.execute();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test @DisplayName("Should throw UnauthorizedException when getId and user is not authenticated")
    void getIdTestCase1() {
        // Given
        SecurityContextHolder.getContext().setAuthentication(null);

        // When & Then
        assertThrows(UnauthorizedException.class, () -> {
            getAuthenticatedUserUseCase.getId();
        });
    }

    @Test @DisplayName("Should return user id when user is authenticated")
    void getIdTestCase2() {
        // Given
        UUID userId = UUID.randomUUID();

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserId(userId);
        userCredentials.setUsername("username");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userCredentials, null, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        UUID result = getAuthenticatedUserUseCase.getId();

        // Then
        assertEquals(userId, result);
    }
}