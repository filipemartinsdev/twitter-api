package com.api.twitter.common.events

import lombok.Getter
import org.springframework.context.ApplicationEvent
import java.util.*

@Getter
class UserDeletedEvent(val userId: UUID, source: Any) : ApplicationEvent(source)
