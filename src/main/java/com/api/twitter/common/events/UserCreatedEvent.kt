package com.api.twitter.common.events;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

data class UserCreatedEvent(
    val userId: UUID,
    val username: String,
    val email: String,
    private val objSource: Any
) : ApplicationEvent(objSource) {
}

