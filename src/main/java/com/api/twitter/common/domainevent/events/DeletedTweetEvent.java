package com.api.twitter.common.domainevent.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class DeletedTweetEvent extends ApplicationEvent {
    @Getter
    private UUID userId;

    public DeletedTweetEvent(UUID userId, Object source) {
        super(source);
        this.userId = userId;
    }
}
