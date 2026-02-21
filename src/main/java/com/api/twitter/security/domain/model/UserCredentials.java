package com.api.twitter.security.domain.model;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.exception.UserValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "user_credentials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    private String username;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role.getName())
        );
    }

    public void validate(){
        this.validateUsername();
        this.validateEmail();
        this.validatePassword();
    }

    // TODO: IMPROVE VALIDATION RESPONSE
    public void validateUsername(){
        final int MIN_LEN_USERNAME = 3;
        final int MAX_LEN_USERNAME = 25;

        if (
                this.username == null ||
                        this.username.length() < MIN_LEN_USERNAME ||
                        this.username.length() > MAX_LEN_USERNAME
        ){
            throw new UserValidationException("Invalid username");
        }
    }

    public void validateEmail(){
        // TODO
    }

    // TODO: IMPROVE VALIDATION RESPONSE
    public void validatePassword(){
        final int MIN_LEN_PASSWORD = 5;
        final int MAX_LEN_PASSWORD = 50;
        if (
                this.password == null ||
                        this.password.length() < MIN_LEN_PASSWORD ||
                        this.password.length() > MAX_LEN_PASSWORD
        ){
            throw new UserValidationException("Invalid password");
        }
    }

    public boolean equals(UserCredentials user2){
        return this.userId.compareTo(user2.getUserId()) == 0;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
