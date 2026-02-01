package com.api.twitter.user.application.domainevent.listeners;

import com.api.twitter.user.application.domainevent.events.CreateFollowEvent;
import com.api.twitter.user.application.usecases.UpdateUserFollowersCountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateFollowEventListener implements ApplicationListener<CreateFollowEvent> {
    @Autowired
    private UpdateUserFollowersCountUseCase updateUserFollowersCountUseCase;

    @Override
    @Transactional
    public void onApplicationEvent(CreateFollowEvent event) {
        updateUserFollowersCountUseCase.incrementFollowersOfUser(event.getFollowingId());
        updateUserFollowersCountUseCase.incrementFollowingOfUser(event.getFollowerId());
    }
}
