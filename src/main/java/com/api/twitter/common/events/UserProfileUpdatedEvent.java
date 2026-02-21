package com.api.twitter.common.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class UserProfileUpdatedEvent extends ApplicationEvent {
    private final UUID userId;
    private final String username;
    private final String email;

    public UserProfileUpdatedEvent(UUID userId, String username, String email, Object source) {
        super(source);
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
