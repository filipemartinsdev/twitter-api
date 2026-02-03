package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.user.application.usecases.GetUserEntityStrictUseCase;
import com.api.twitter.user.domain.User;
import com.api.twitter.user.relationship.domain.UserRelationship;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FollowUserUseCase {
    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private GetUserEntityStrictUseCase getUserEntityStrictUseCase;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void execute(UUID followerId, UUID followingId){
        if (userRelationshipRepository.existsByFollowerIdAndFollowingId(followerId, followingId))
            throw new BadRequestException("Its already following this user");

        if(followerId.compareTo(followingId) == 0)
            throw new BadRequestException("Can't follow yourself");

        User follower = getUserEntityStrictUseCase.execute(followerId);
        User following = getUserEntityStrictUseCase.execute(followingId);

        var userRelationship = new UserRelationship(follower, following, getNow());
        userRelationshipRepository.save(userRelationship);
    }

    private LocalDateTime getNow(){
        return LocalDateTime.now();
    }
}
