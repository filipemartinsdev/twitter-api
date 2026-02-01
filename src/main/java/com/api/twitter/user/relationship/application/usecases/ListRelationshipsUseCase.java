package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListRelationshipsUseCase {
    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerifyUserUseCase verifyUserUseCase;

    public PagedResponse<UserResponse> getAllFollowersOfUser(UUID userId, Pageable pageable){

        if (!verifyUserUseCase.existsById(userId))
            throw new BadRequestException("User not found");

        Page<User> userPage = userRelationshipRepository.findAllFollowersOfUser(userId, pageable);

        return PagedResponse.<UserResponse>builder()
                .size(userPage.getSize())
                .page(userPage.getNumber())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .isLast(userPage.isLast())
                .content(userPage.getContent().stream()
                        .map(user -> userMapper.toResponse(user))
                        .toList()
                )
                .build();
    }

    public PagedResponse<UserResponse> getAllFollowingOfUser(UUID userId, Pageable pageable){
        Page<User> userPage = userRelationshipRepository.findAllFollowingOfUser(userId, pageable);

        return PagedResponse.<UserResponse>builder()
                .size(userPage.getSize())
                .page(userPage.getNumber())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .isLast(userPage.isLast())
                .content(userPage.getContent().stream()
                        .map(user -> userMapper.toResponse(user))
                        .toList()
                )
                .build();
    }
}
