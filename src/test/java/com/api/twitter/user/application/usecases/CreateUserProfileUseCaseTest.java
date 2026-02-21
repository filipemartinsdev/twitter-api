package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
class CreateUserProfileUseCaseTest {
    @Mock
    private UserProfileRepository userRepository;

    @InjectMocks
    private CreateUserProfileUseCase createUserUseCase;

    private AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    private final UserProfile userMock1 = new UserProfile(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "desc",
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should create the user if its OK")
    public void executeTestCase1(){
        Mockito.when(userRepository.save(any()))
                .thenReturn(userMock1);
        Mockito.when(userRepository.existsByUsername(any()))
                .thenReturn(false);
        Mockito.when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        createUserUseCase.execute(
                userMock1.getUserId(),
                userMock1.getUsername(),
                userMock1.getEmail()
//                "encrypted"
        );

        ArgumentCaptor<UserProfile> userCaptor = ArgumentCaptor.forClass(UserProfile.class);
        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(userMock1.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(userMock1.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).save(userCaptor.capture());

        UserProfile savedUser = userCaptor.getValue();
        assertEquals(userMock1.getUserId(), savedUser.getUserId());
        assertEquals(userMock1.getUsername(), savedUser.getUsername());
        assertEquals(userMock1.getEmail(), savedUser.getEmail());
    }

    @Test
    @DisplayName("Should not create the user if its already exists")
    public void executeTestCase2(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            createUserUseCase.execute(
                    userMock1.getUserId(),
                    userMock1.getUsername(),
                    userMock1.getEmail()
//                    "encrypted"
            );
        });

        Mockito.verify(userRepository, Mockito.times(0)).save(any());
    }

    @Test
    @DisplayName("Should not create the user if email already exists")
    public void executeTestCase3(){
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> {
            createUserUseCase.execute(
                    userMock1.getUserId(),
                    userMock1.getUsername(),
                    userMock1.getEmail()
//                    "encrypted"
            );
        });

        Mockito.verify(userRepository, Mockito.times(0)).save(any());
    }
}
