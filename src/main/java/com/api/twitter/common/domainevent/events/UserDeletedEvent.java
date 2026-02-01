package com.api.twitter.common.domainevent.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class UserDeletedEvent extends ApplicationEvent {
    private UUID userId;

    public UserDeletedEvent(UUID userId, Object source) {
        super(source);

        if (userId == null) {
            throw new RuntimeException("userId can't be null");
        }

        this.userId = userId;
    }
}
