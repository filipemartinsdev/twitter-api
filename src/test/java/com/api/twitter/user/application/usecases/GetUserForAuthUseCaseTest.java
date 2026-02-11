package com.api.twitter.user.application.usecases;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetUserForAuthUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserForAuthUseCase getUserForAuthUseCase;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(mocks != null)
            mocks.close();
    }

    private final User userMock1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "12345",
            UserRole.USER,
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should successfully get the user for auth")
    void executeTestCase1() {
        String username = userMock1.getUsername();

        Mockito.when(userRepository.existsByUsername(username))
                .thenReturn(true);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(userMock1));

        getUserForAuthUseCase.execute(username);

        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(username);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user does not exist")
    void executeTestCase2() {
        String username = "test";

        Mockito.when(userRepository.existsByUsername(username))
                .thenReturn(false);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            getUserForAuthUseCase.execute(username);
        });
    }
}