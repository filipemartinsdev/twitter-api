package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.exception.UserValidationException;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    private final User userMock1 = new User(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "12345",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final User userInvalidMock1 = new User(
            UUID.randomUUID(),
            " ",
            "@#$",
            " ",
            UserRole.USER,
            LocalDateTime.now()
    );

    private final String encryptedPasswordMock = "bd0ef7878fb434715c14a6243e89cdcd";

    @Test
    @DisplayName("Should create the user if its OK")
    public void executeTestCase1(){
        try (MockedConstruction<User> mockUser = Mockito.mockConstruction( User.class,  (user, context) -> {
            Mockito.doNothing().when(user).validateUsername();
            Mockito.doNothing().when(user).validateEmail();
            Mockito.doNothing().when(user).validatePassword();
        })){
        Mockito.when(userRepository.save(any()))
                .thenReturn(userMock1);
        Mockito.when(userRepository.existsByUsername(any()))
                .thenReturn(false);
        Mockito.when(userRepository.existsByEmail(any()))
                .thenReturn(false);
        Mockito.when(passwordEncoder.encode(any()))
                .thenReturn(encryptedPasswordMock);

        createUserUseCase.execute(
                userMock1.getUsername(),
                userMock1.getEmail(),
                userMock1.getPassword()
        );

        User constructedUser = mockUser.constructed().get(0);

        Mockito.verify(constructedUser, Mockito.times(1)).validateUsername();
        Mockito.verify(constructedUser, Mockito.times(1)).validateEmail();
        Mockito.verify(constructedUser, Mockito.times(1)).validatePassword();

        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(userMock1.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(userMock1.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        }
    }

    @Test
    @DisplayName("Should not create the user if its already exists")
    public void executeTestCase2(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            createUserUseCase.execute(
                    userMock1.getUsername(),
                    userMock1.getEmail(),
                    userMock1.getPassword()
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
                        userInvalidMock1.getUsername(),
                        userInvalidMock1.getEmail(),
                        userInvalidMock1.getPassword()
                );
            });

            Mockito.verify(userRepository, Mockito.times(0)).save(any());
        }
    }
}