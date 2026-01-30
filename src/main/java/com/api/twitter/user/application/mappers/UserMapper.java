package com.api.twitter.user.application.mappers;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user){
        var userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .tweetsCount(user.getTweetsCount())
                .build();
        return userResponse;
    }

    public PagedResponse<UserResponse> toPagedUserResponse(Page<User> userPage){
        return PagedResponse.<UserResponse>builder()
                .size(userPage.getSize())
                .page(userPage.getNumber())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .isLast(userPage.isLast())
                .content(userPage.getContent().stream()
                        .map(this::toResponse)
                        .toList()
                )
                .build();
    }
}
