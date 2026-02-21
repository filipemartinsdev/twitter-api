package com.api.twitter.user.application.events.listeners;

import com.api.twitter.common.events.UserCreatedEvent;
import com.api.twitter.user.application.usecases.CreateUserProfileUseCase;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventListener implements ApplicationListener<UserCreatedEvent> {
    private CreateUserProfileUseCase createUserProfileUseCase;

    public UserCreatedEventListener(CreateUserProfileUseCase createUserProfileUseCase){
        this.createUserProfileUseCase = createUserProfileUseCase;
    }

    @Override
    public void onApplicationEvent(UserCreatedEvent event) {
        createUserProfileUseCase.execute(
                event.getUserId(),
                event.getUsername(),
                event.getEmail()
        );
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
