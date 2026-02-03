package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.user.relationship.domain.UserRelationship;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsultRelationshipsCount {
    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    public long getFollowersCountOfUser(UUID userID){
        return userRelationshipRepository.getFollowersCountOfUser(userID);
    }

    public long getFollowingCountOfUser(UUID userID){
        return userRelationshipRepository.getFollowingCountOfUser(userID);
    }
}
