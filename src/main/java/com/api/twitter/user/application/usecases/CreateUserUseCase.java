package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CacheEvict(value = "userPages", allEntries = true)
    public void execute(String username, String email, String encryptedPassword){
        if(userRepository.existsByUsername(username))
            throw new BadRequestException("This username is already in use");
        if(userRepository.existsByEmail(email))
            throw new BadRequestException("This email is already in use");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);
        user.setCreatedAt(LocalDateTime.now());

        user.validateUsername();
        user.validateEmail();
        user.validatePassword();

        userRepository.save(user);
    }
}
