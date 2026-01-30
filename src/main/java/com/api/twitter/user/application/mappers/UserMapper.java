package com.api.twitter.user.application.mappers;

import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.domain.User;
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
}
