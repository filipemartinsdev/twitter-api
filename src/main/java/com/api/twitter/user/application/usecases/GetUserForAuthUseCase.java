package com.api.twitter.user.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.security.domain.model.UserDetailsImpl;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetUserForAuthUseCase {
    @Autowired
    private UserRepository userRepository;

    public UserDetails execute(String username){
        User user =  this.userRepository.findByUsername(username)
                .orElse(null);
        if (user == null)
//            return null;
            throw new UsernameNotFoundException("User not found");


        var userDetailsImpl = new UserDetailsImpl();
        userDetailsImpl.setId(user.getId());
        userDetailsImpl.setUsername(user.getUsername());
        userDetailsImpl.setPassword(user.getPassword());
        userDetailsImpl.setRole(user.getRole());

        return userDetailsImpl;
    }
}
