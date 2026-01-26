package com.api.twitter.common.domainevent.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.UUID;


@Getter
public class UnfollowUserEvent extends ApplicationEvent  {
    private UUID followingId;
    private UUID followerId;

    public UnfollowUserEvent(UUID followingId, UUID followerId, Object source) {
        super(source);
        this.followerId = followerId;
        this.followingId = followingId;
    }

    public UnfollowUserEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
