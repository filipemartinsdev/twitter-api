package com.api.twitter.user.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Cacheable(value = "userPages", key = "#page", condition="#page!=null")
    public PagedResponse<UserResponse> getAll(Pageable pageable) {
        Page<User> userPage =  userRepository.findAll(pageable);
        return userMapper.toPagedUserResponse(userPage);
    }

    @Cacheable(value = "userPages", key = "#page", condition="#page!=null")
    public PagedResponse<UserResponse> query(String query, Pageable pageable) {
        Page<User> userPage = userRepository.findAllByUsernameContainingIgnoreCase(query, pageable);
        return userMapper.toPagedUserResponse(userPage);
    }
}
