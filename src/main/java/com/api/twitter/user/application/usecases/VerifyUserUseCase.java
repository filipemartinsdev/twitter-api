package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerifyUserUseCase {
    @Autowired
    private UserRepository userRepository;

    public boolean existsById(UUID id){
        return this.userRepository.existsById(id);
    }

    public boolean existsByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    @Deprecated
    public void verifyIfUsernameAndEmailIsAlreadyInUse(String username, String email){
        if (userRepository.existsByUsername(username)){
            throw new BadRequestException("This username is already in use");
        }

        if (userRepository.existsByEmail(email)){
            throw new BadRequestException("This email is already in use");
        }
    }
}
