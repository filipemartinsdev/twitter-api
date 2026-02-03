package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.BadRequestException;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class UnfollowUserUseCase {
    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private VerifyUserUseCase verifyUserUseCase;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void execute(UUID followerId, UUID followingId){
        if (!userRelationshipRepository.existsByFollowerIdAndFollowingId(followerId, followingId))
            throw new BadRequestException("Its not following this user yet");

        if (
                !verifyUserUseCase.existsById(followerId) ||
                !verifyUserUseCase.existsById(followerId)
        ){
            throw new NotFoundException("User not found");
        }
        userRelationshipRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }
}
