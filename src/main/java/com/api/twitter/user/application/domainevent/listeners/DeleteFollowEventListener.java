package com.api.twitter.user.application.domainevent.listeners;

import com.api.twitter.user.application.domainevent.events.DeleteFollowEvent;
import com.api.twitter.user.application.usecases.UpdateUserFollowersCountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteFollowEventListener implements ApplicationListener<DeleteFollowEvent> {
    @Autowired
    private UpdateUserFollowersCountUseCase updateUserFollowersCountUseCase;

    @Override
    @Transactional
    public void onApplicationEvent(DeleteFollowEvent event) {
        updateUserFollowersCountUseCase.decrementFollowingOfUser(event.getFollowerId());
        updateUserFollowersCountUseCase.decrementFollowersOfUser(event.getFollowingId());
    }
}
