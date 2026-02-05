package com.api.twitter.user.application.usecases;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.security.application.dto.UserLoginRequest;
import com.api.twitter.security.application.dto.UserRegisterRequest;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;


import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
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
            UserRole.USER
    );

    private final User mockedUser2 = new User(
            UUID.randomUUID(),
            "test2",
            "test2@gmail.com",
            "test2",
            UserRole.USER
    );

    private final  User mockedUserInvalid = new User(
            UUID.randomUUID(),
            " ",
            "@#$",
            " ",
            UserRole.USER
    );

    @Test
    @DisplayName("Should create the user if its OK")
    public void executeTestCase1(){
        Mockito.when(userRepository.save(any())).thenReturn(mockedUser1);

        createUserUseCase.execute(
                mockedUser1.getUsername(),
                mockedUser1.getEmail(),
                mockedUser1.getPassword()
        );

        Mockito.verify(userRepository, Mockito.times(1)).save(mockedUser1);
    }

    @Test
    @DisplayName("Should not create the user if its invalid")
    public void executeTestCase2(){
        createUserUseCase.execute(
                mockedUserInvalid.getUsername(),
                mockedUserInvalid.getEmail(),
                mockedUserInvalid.getPassword()
        );

        Mockito.verify(any(User.class)).validateUsername();
        Mockito.verify(any(User.class)).validatePassword();
        Mockito.verify(any(User.class)).validateEmail();
    }

    @Test
    @DisplayName("Should not create the user if its already exists")
    public void executeTestCase3(){

    }
}