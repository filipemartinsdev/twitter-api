package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListUsersUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserResponse getById(UUID id){
        return userMapper.toResponse(
                userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("User not found"))
        );
    }

    public UserResponse getByUsername(String username) {
        return userMapper.toResponse(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found"))
        );
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toResponse(user))
                .toList();

    }
}
