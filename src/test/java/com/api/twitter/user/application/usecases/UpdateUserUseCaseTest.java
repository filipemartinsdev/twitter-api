package com.api.twitter.user.application.usecases;

import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUserCse;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(mock!=null){
            mock.close();
        }
    }

    @Test
    @DisplayName("Should ")
    void executeTestCase1() {

    }
}