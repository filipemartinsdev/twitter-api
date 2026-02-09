package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.model.UserRole;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import com.api.twitter.user.application.exception.UserValidationException;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private final User mockedUser1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "test",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final User mockedUser2 = new User(
            UUID.randomUUID(),
            "test2",
            "test2@gmail.com",
            "test2",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final  User mockedUserInvalid = new User(
            UUID.randomUUID(),
            " ",
            "@#$",
            " ",
            UserRole.USER,
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should create the user if its OK")
    public void executeTestCase1(){
        Mockito.when(userRepository.save(any())).thenReturn(mockedUser1);
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(false);

        createUserUseCase.execute(
                mockedUser1.getUsername(),
                mockedUser1.getEmail(),
                mockedUser1.getPassword()
        );

        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(mockedUser1.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(mockedUser1.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Should not create the user if its already exists")
    public void executeTestCase2(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            createUserUseCase.execute(
                    mockedUser1.getUsername(),
                    mockedUser1.getEmail(),
                    mockedUser1.getPassword()
            );
        });
    }

    @Test
    @DisplayName("Should not create the user if it's invalid")
    public void executeTestCase3(){
        try (MockedConstruction<User> mockUser = Mockito.mockConstruction(User.class, (mock, context) -> {
            Mockito.doThrow(new UserValidationException(""))
                    .when(mock).validateUsername();
            Mockito.doThrow(new UserValidationException(""))
                    .when(mock).validateEmail();
            Mockito.doThrow(new UserValidationException(""))
                    .when(mock).validatePassword();
        })) {
            Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
            Mockito.when(userRepository.existsByEmail(any())).thenReturn(false);

            assertThrows(UserValidationException.class, () -> {
                createUserUseCase.execute(
                        mockedUserInvalid.getUsername(),
                        mockedUserInvalid.getEmail(),
                        mockedUserInvalid.getPassword()
                );
            });

            Mockito.verify(userRepository, Mockito.times(0)).save(any());
        }
    }
}