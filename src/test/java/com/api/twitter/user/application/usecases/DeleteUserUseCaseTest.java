package com.api.twitter.user.application.usecases;

import com.api.twitter.common.events.UserDeletedEvent;
import com.api.twitter.user.application.exception.UserNotExists;
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
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserUseCaseTest {
    @Mock
    private UserProfileRepository userRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) mocks.close();
    }

    private final UserProfile userMock1 = new UserProfile(
            UUID.randomUUID(),
            "test",
            "test@gmail.com",
            "desc",
            LocalDateTime.now()
    );

    @Test
    @DisplayName("Should delete successfully the user")
    void deleteByIdTestCase1() {
        Mockito.when(userRepository.existsById(userMock1.getUserId()))
                .thenReturn(true);

        deleteUserUseCase.deleteById(userMock1.getUserId());

        Mockito.verify(userRepository, Mockito.times(1))
                .deleteById(userMock1.getUserId());

        ArgumentCaptor<UserDeletedEvent> eventCaptor = ArgumentCaptor.forClass(UserDeletedEvent.class);
        Mockito.verify(applicationEventPublisher, Mockito.times(1))
                .publishEvent(eventCaptor.capture());
        assertEquals(userMock1.getUserId(), eventCaptor.getValue().getUserId());
    }

    @Test
    @DisplayName("Should throw UserNotExists when user does not exist")
    void deleteByIdTestCase2() {
        Mockito.when(userRepository.existsById(userMock1.getUserId()))
                .thenReturn(false);

        assertThrows(UserNotExists.class, () ->
                deleteUserUseCase.deleteById(userMock1.getUserId())
        );

        Mockito.verify(userRepository, Mockito.times(0)).deleteById(Mockito.any());
        Mockito.verify(applicationEventPublisher, Mockito.times(0)).publishEvent(Mockito.any());
    }


}
