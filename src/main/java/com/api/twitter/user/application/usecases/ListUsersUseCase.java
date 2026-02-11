package com.api.twitter.user.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.dto.UserAndCounts;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.exception.UserNotExists;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
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
        UserAndCounts userAndCounts = userRepository.findUserAndCountsById(id)
                .orElseThrow(() -> new UserNotExists("User not found"));

        return userMapper.toResponse(userAndCounts);
    }

    @Cacheable(value = "userPages", key = "#page", condition="#page!=null")
    public PagedResponse<UserResponse> getAll(Pageable pageable) {
        Page<UserAndCounts> userPage =  userRepository.findAllUserAndCounts(pageable);

        if(userPage == null){
            return PagedResponse.<UserResponse>builder()
                    .size(0)
                    .page(0)
                    .totalPages(0)
                    .totalElements(0L)
                    .isLast(true)
                    .content(List.of())
                    .build();
        }

        return userMapper.toPagedUserResponse(userPage);
    }

    @Cacheable(value = "userPages", key = "#page", condition="#page!=null")
    public PagedResponse<UserResponse> query(String query, Pageable pageable) {
        Page<UserAndCounts> userPage = userRepository.findAllUserAndCountsByUsernameLike(query, pageable);

        if(userPage == null){
            return PagedResponse.<UserResponse>builder()
                    .size(0)
                    .page(0)
                    .totalPages(0)
                    .totalElements(0L)
                    .isLast(true)
                    .content(List.of())
                    .build();
        }
        return userMapper.toPagedUserResponse(userPage);
    }
}
