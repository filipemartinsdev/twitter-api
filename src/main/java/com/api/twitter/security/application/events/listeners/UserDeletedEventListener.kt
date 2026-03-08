package com.api.twitter.security.application.events.listeners

import com.api.twitter.common.events.UserDeletedEvent
import com.api.twitter.security.application.usecases.DeleteUserCredentialsUseCase
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class UserDeletedEventListener(private val deleteUserCredentialsUseCase: DeleteUserCredentialsUseCase)
    : ApplicationListener<UserDeletedEvent> {
    override fun onApplicationEvent(event: UserDeletedEvent) {
        deleteUserCredentialsUseCase.execute(event.userId)
    }

    override fun supportsAsyncExecution(): Boolean {
        return super.supportsAsyncExecution()
    }
}
