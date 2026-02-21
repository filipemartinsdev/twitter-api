package com.api.twitter.common.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class UserCredentialsUpdatedEvent extends ApplicationEvent {
    private UUID userId;
    private String username;
    private String email;
    private String encryptedPassword;

    public UserCredentialsUpdatedEvent(UUID userId, String username, String email, String encryptedPassword, Object source) {
        super(source);
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public UserCredentialsUpdatedEvent(Object source){
        super(source);
    }
}
