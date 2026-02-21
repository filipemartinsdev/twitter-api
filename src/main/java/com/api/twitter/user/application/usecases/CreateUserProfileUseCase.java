package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.domain.UserProfile;
import com.api.twitter.user.infrastructure.persistence.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CreateUserProfileUseCase {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Caching(evict = {
            @CacheEvict(value = "usersByPageSizeSort", allEntries = true),
            @CacheEvict(value = "userQueryByNumberSizeSort", allEntries = true)
    })
    public void execute(UUID userId, String username, String email){
        if(userProfileRepository.existsByUsername(username))
            throw new BadRequestException("This username is already in use");
        if(userProfileRepository.existsByEmail(email))
            throw new BadRequestException("This email is already in use");

        UserProfile user = new UserProfile(userId, username, email, null, LocalDateTime.now());
        userProfileRepository.save(user);
    }
}
