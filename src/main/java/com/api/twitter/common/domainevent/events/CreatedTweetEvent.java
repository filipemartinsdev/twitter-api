package com.api.twitter.common.domainevent.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class CreatedTweetEvent extends ApplicationEvent {
    @Getter
    private UUID userId;

    public CreatedTweetEvent(UUID userId, Object source) {
        super(source);
        this.userId = userId;
    }
}
