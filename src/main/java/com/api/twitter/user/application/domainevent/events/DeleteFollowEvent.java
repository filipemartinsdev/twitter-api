package com.api.twitter.user.application.domainevent.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class DeleteFollowEvent extends ApplicationEvent {
    private UUID followerId;
    private UUID followingId;

    public DeleteFollowEvent(UUID followerId, UUID followingId, Object source) {
        super(source);
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
