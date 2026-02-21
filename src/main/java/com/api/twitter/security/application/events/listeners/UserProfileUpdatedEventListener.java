package com.api.twitter.security.application.events.listeners;

import com.api.twitter.common.events.UserProfileUpdatedEvent;
import com.api.twitter.security.application.usecases.UpdateUserCredentialsUseCase;
import org.springframework.context.ApplicationListener;

public class UserProfileUpdatedEventListener implements ApplicationListener<UserProfileUpdatedEvent> {
    private UpdateUserCredentialsUseCase updateUserCredentialsUseCase;

    public UserProfileUpdatedEventListener(UpdateUserCredentialsUseCase updateUserCredentialsUseCase) {
        this.updateUserCredentialsUseCase = updateUserCredentialsUseCase;
    }

    @Override
    public void onApplicationEvent(UserProfileUpdatedEvent event) {
        updateUserCredentialsUseCase.execute(event.getUserId(), event.getUsername(), event.getEmail());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
