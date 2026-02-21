package com.api.twitter.user.application.mappers;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.user.application.dto.UserAndCounts;
import com.api.twitter.user.application.dto.UserProfileResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(UserAndCounts userAndCounts){
        return UserResponse.builder()
                .id(userAndCounts.getId())
                .username(userAndCounts.getUsername())
                .email(userAndCounts.getEmail())
                .tweetsCount(userAndCounts.getTweetsCount())
                .followersCount(userAndCounts.getFollowersCount())
                .followingCount(userAndCounts.getFollowingCount())
                .build();
    }

    public PagedResponse<UserResponse> toPagedUserResponse(Page<UserAndCounts> userAndCountsPage){
        return PagedResponse.<UserResponse>builder()
                .page(userAndCountsPage.getNumber())
                .size(userAndCountsPage.getSize())
                .isLast(userAndCountsPage.isLast())
                .totalElements(userAndCountsPage.getTotalElements())
                .totalPages(userAndCountsPage.getTotalPages())
                .content(userAndCountsPage.getContent().stream()
                        .map(userAndCounts -> UserResponse.builder()
                                .id(userAndCounts.getId())
                                .username(userAndCounts.getUsername())
                                .email(userAndCounts.getEmail())
                                .followersCount(userAndCounts.getFollowersCount())
                                .followingCount(userAndCounts.getFollowingCount())
                                .tweetsCount(userAndCounts.getTweetsCount())
                                .build())
                        .toList()
                )
                .build();
    }

    @Deprecated
    public UserResponse toResponse(UserProfile user){
        var userResponse = UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        return userResponse;
    }

    public UserProfileResponse toProfileResponse(UserProfile user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .description(user.getDescription())
                .build();
    }
}
