package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.dto.UserProfileResponse;
import com.api.twitter.user.application.dto.UserUpdateRequest;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
class UpdateUserUseCaseTest {
    @Mock
    private UserProfileRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserUseCase updateUserUserCse;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mock != null) {
            mock.close();
        }
    }

    private final UserProfile userProfileMock1 = new UserProfile(
            UUID.randomUUID(),
            "test1",
            "test1@example.com",
            "test1",
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should update description when user exists")
    void updateUserTestCase1() {
        String newDescription = "new description";

        var newUserProfile = new UserProfile(
                userProfileMock1.getUserId(),
                userProfileMock1.getUsername(),
                userProfileMock1.getEmail(),
                newDescription,
                userProfileMock1.getCreatedAt()
        );

        Mockito.when(userRepository.existsById(userProfileMock1.getUserId()))
                .thenReturn(true);
        Mockito.when(userRepository.findById(userProfileMock1.getUserId()))
                .thenReturn(Optional.of(newUserProfile));
        Mockito.when(userRepository.save(any(UserProfile.class)))
                .thenReturn(newUserProfile);
        Mockito.when(userMapper.toProfileResponse(newUserProfile))
                .thenReturn(new UserProfileResponse(
                        newUserProfile.getUserId(),
                        newUserProfile.getUsername(),
                        newUserProfile.getEmail(),
                        newUserProfile.getDescription()
                ));

        UserUpdateRequest request = new UserUpdateRequest(
                Optional.empty(),
                Optional.empty(),
                Optional.of(newDescription)
        );

        UserProfileResponse response = updateUserUserCse.updateUser(userProfileMock1.getUserId(), request);

//        Mockito.verify(userProfileMock1, Mockito.times(1))
//                .setDescription(newDescription);

        assertEquals(newDescription, response.description());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user does not exist")
    void updateUserTestCase2() {
        UUID userId = UUID.randomUUID();
        UserUpdateRequest request = new UserUpdateRequest(
                null,
                null,
                null
        );

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> updateUserUserCse.updateUser(userId, request));

        Mockito.verify(userRepository, Mockito.times(0))
                .save(any(UserProfile.class));
    }
}
