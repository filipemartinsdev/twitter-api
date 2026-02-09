package com.api.twitter.security.application.usecases;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
public class GetAuthenticatedUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    private final User userMock1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "test",
            UserRole.USER,
            LocalDateTime.now()
    );

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should retrieve the authenticated user details successfully")
    public void executeTestCase1(){
        var userAndPassToken = new UsernamePasswordAuthenticationToken(
                userMock1.getUsername(),
                userMock1.getPassword()
        );
        var auth = authenticationManager.authenticate(userAndPassToken);

        SecurityContextHolder.getContext().setAuthentication(auth);

        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(auth);

        Mockito.when(userRepository.findByUsername(any())).thenReturn(Optional.of(userMock1));
    }
}