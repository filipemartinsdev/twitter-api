package com.api.twitter.user.domain;

import com.api.twitter.security.domain.model.UserCredentials;
import com.api.twitter.user.application.exception.UserValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "user_profile")
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class UserProfile {
    @Id
    private UUID userId;

    @NotEmpty
    private String username;

    @Email
    private String email;

    private String description;

    private LocalDateTime createdAt;

    public void validate(){
        this.validateUsername();
        this.validateEmail();
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

    public boolean equals(UserCredentials user2){
        return this.userId.compareTo(user2.getUserId()) == 0;
    }
}
