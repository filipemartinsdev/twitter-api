package com.api.twitter.common.events;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.UUID;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final UUID userId;
    private final String username;
    private final String email;

    public UserCreatedEvent(
            UUID userId,
            String username,
            String email,
            Object source
    ) {
        super(source);
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
