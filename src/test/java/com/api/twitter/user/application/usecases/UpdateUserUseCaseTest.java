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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Should update description when user exists")
    void updateUserTestCase1() {
        UUID userId = UUID.randomUUID();
        UserProfile user = new UserProfile(
                userId,
                "old-user",
                "old@example.com",
                "old-desc",
                LocalDateTime.now()
        );

        UserUpdateRequest request = new UserUpdateRequest(
                JsonNullable.of("new-user"),
                null,
                JsonNullable.of("new-desc")
        );

        UserProfileResponse response = UserProfileResponse.builder()
                .userId(userId)
                .username(user.getUsername())
                .email(user.getEmail())
                .description("new-desc")
                .build();

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(UserProfile.class)))
                .thenReturn(user);
        Mockito.when(userMapper.toProfileResponse(Mockito.any(UserProfile.class)))
                .thenReturn(response);

        UserProfileResponse result = updateUserUserCse.updateUser(userId, request);

        ArgumentCaptor<UserProfile> userCaptor = ArgumentCaptor.forClass(UserProfile.class);
        Mockito.verify(userRepository, Mockito.times(1)).save(userCaptor.capture());

        UserProfile savedUser = userCaptor.getValue();
        assertEquals("old-user", savedUser.getUsername());
        assertEquals("new-desc", savedUser.getDescription());
        assertEquals(response, result);
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

        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any(UserProfile.class));
    }
}
