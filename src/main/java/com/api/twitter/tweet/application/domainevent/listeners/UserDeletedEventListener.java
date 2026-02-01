package com.api.twitter.tweet.application.domainevent.listeners;

import com.api.twitter.common.domainevent.events.UserDeletedEvent;
import com.api.twitter.tweet.application.usecases.DeleteTweetUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

public class UserDeletedEventListener implements ApplicationListener<UserDeletedEvent> {
    @Autowired
    private DeleteTweetUseCase deleteTweetUseCase;

    @Override
    public void onApplicationEvent(UserDeletedEvent event) {
        deleteTweetUseCase.deleteAllOfUser(event.getUserId());
    }
}
