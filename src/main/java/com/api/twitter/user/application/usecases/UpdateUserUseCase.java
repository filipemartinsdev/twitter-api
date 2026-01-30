package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
public class UpdateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserResponse execute(UUID id, Map<String, Object> fields){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        fields.forEach((key, value) -> {
            switch (key){
                case "username":
                    String username;
                    try {
                        username = (String) value;
                    } catch (RuntimeException exception){
                        throw new BadRequestException("invalid username");
                    }
                    user.setUsername(username);
                    user.validateUsername();
                break;

                case "email":
                    String email;
                    try {
                        email = (String) value;
                    } catch (RuntimeException exception){
                        throw new BadRequestException("invalid email");
                    }
                    user.setEmail(email);
                    //TODO: VALIDATE EMAIL
                break;

                case "password":
                    String password;
                    try {
                        password = (String) value;
                    } catch (RuntimeException exception){
                        throw new BadRequestException("invalid email");
                    }
                    user.setPassword(password);
                    user.validatePassword();
                break;

                default:
                    throw new BadRequestException("Invalid field: '"+key+"'");
            }
        });

        return userMapper.toResponse(userRepository.save(user));
    }
}
