package com.api.twitter.user.application.usecases;

import com.api.twitter.user.application.exception.UserNotExists;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private AutoCloseable mocks;

    @BeforeEach
    void setup(){
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) mocks.close();
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
    @DisplayName("Should delete successfully the user")
    void deleteByIdTestCase1() {
        Mockito.when(userRepository.existsById(userMock1.getId()))
                .thenReturn(true);

        deleteUserUseCase.deleteById(userMock1.getId());

        Mockito.verify(userRepository, Mockito.times(1))
                .deleteById(userMock1.getId());
    }

    @Test
    @DisplayName("Should throw UserNotExists when user does not exist")
    void deleteByIdShouldThrowWhenUserNotExists() {
        Mockito.when(userRepository.existsById(userMock1.getId()))
                .thenReturn(false);

        assertThrows(UserNotExists.class, () ->
                deleteUserUseCase.deleteById(userMock1.getId())
        );

        Mockito.verify(userRepository, Mockito.times(0)).deleteById(Mockito.any());
    }


}