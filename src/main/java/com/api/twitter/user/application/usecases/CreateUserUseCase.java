package com.api.twitter.user.application.usecases;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    public void execute(String username, String email, String encryptedPassword){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);
        user.setFollowersCount(0L);
        user.setFollowingCount(0L);
        user.setTweetsCount(0L);
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }
}
