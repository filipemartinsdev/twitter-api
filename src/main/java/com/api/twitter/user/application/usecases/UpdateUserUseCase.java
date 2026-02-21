package com.api.twitter.user.application.usecases;

import com.api.twitter.common.events.UserCredentialsUpdatedEvent;
import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.security.domain.model.UserCredentials;
import com.api.twitter.user.application.dto.UserProfileResponse;
import com.api.twitter.user.application.dto.UserResponse;
import com.api.twitter.user.application.dto.UserUpdateRequest;
import com.api.twitter.user.application.mappers.UserMapper;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
public class UpdateUserUseCase {
    @Autowired
    private UserProfileRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Caching(evict = {
            @CacheEvict(value = "usersByPageSizeSort", allEntries = true),
            @CacheEvict(value = "userQueryByNumberSizeSort", allEntries = true),
            @CacheEvict(value = "userById", key = "#userId.toString()")
    })
    public UserProfileResponse updateUser(UUID userID, UserUpdateRequest request){
            UserProfile user = userRepository.findById(userID)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if(request.username() != null && !request.username().isPresent()){
                user.setUsername(request.username().get());
            }
            if(request.description() != null && request.description().isPresent()){
                user.setDescription(request.description().get());
            }
            user.validate();
            return userMapper.toProfileResponse(userRepository.save(user));
    }
}
