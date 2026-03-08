package com.api.twitter.security.application.events.listeners

import com.api.twitter.common.events.UserProfileUpdatedEvent
import com.api.twitter.security.application.usecases.UpdateUserCredentialsUseCase
import org.springframework.context.ApplicationListener

class UserProfileUpdatedEventListener(private val updateUserCredentialsUseCase: UpdateUserCredentialsUseCase) :
    ApplicationListener<UserProfileUpdatedEvent> {
    override fun onApplicationEvent(event: UserProfileUpdatedEvent) {
        updateUserCredentialsUseCase.execute(event.userId, event.username, event.email)
    }

    override fun supportsAsyncExecution(): Boolean {
        return super.supportsAsyncExecution()
    }
}
