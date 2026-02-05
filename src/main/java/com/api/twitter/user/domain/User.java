package com.api.twitter.user.domain;

import com.api.twitter.common.model.UserRole;
import com.api.twitter.user.application.exception.UserValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Table(name = "users")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    private String username;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // TODO: IMPROVE VALIDATION RESPONSE
    public void validateUsername(){
        final int MIN_LEN_USERNAME = 3;
        final int MAX_LEN_USERNAME = 25;

        if (
                this.username != null ||
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
                this.password != null ||
                this.password.length() < MIN_LEN_PASSWORD ||
                this.password.length() > MAX_LEN_PASSWORD
        ){
            throw new UserValidationException("Invalid password");
        }
    }

    public boolean equals(User user2){
        return this.id.compareTo(user2.getId()) == 0;
    }
}
