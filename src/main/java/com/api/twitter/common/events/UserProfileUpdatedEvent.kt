package com.api.twitter.common.events

import lombok.Getter
import org.springframework.context.ApplicationEvent
import java.util.*

@Getter
class UserProfileUpdatedEvent(
    val userId: UUID,
    val username: String,
    val email: String,
    source: Any
) : ApplicationEvent(source)
