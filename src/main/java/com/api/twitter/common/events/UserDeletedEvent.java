package com.api.twitter.common.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class UserDeletedEvent extends ApplicationEvent {
    private UUID userId;

    public UserDeletedEvent(UUID userId, Object source) {
        super(source);
        this.userId = userId;
    }
}
