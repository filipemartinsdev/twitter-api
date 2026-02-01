package com.api.twitter.user.relationship.application.domainevent.listeners;

import com.api.twitter.common.domainevent.events.UserDeletedEvent;
import com.api.twitter.user.relationship.application.usecases.DeleteRelationshipUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserDeletedEventListener implements ApplicationListener<UserDeletedEvent> {
    @Autowired
    private DeleteRelationshipUseCase deleteRelationshipUseCase;

    @Override
    public void onApplicationEvent(UserDeletedEvent event) {
        deleteRelationshipUseCase.deleteAllByUserId(event.getUserId());
    }
}
