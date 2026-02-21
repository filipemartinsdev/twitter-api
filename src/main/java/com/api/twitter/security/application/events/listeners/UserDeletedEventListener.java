package com.api.twitter.security.application.events.listeners;

import com.api.twitter.common.events.UserDeletedEvent;
import com.api.twitter.security.application.usecases.DeleteUserCredentialsUseCase;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserDeletedEventListener implements ApplicationListener<UserDeletedEvent> {
    private DeleteUserCredentialsUseCase deleteUserCredentialsUseCase;

    public UserDeletedEventListener(DeleteUserCredentialsUseCase deleteUserCredentialsUseCase) {
        this.deleteUserCredentialsUseCase = deleteUserCredentialsUseCase;
    }

    @Override
    public void onApplicationEvent(UserDeletedEvent event) {
        deleteUserCredentialsUseCase.execute(event.getUserId());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
