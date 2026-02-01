package com.api.twitter.user.relationship.application.usecases;

import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.user.application.usecases.VerifyUserUseCase;
import com.api.twitter.user.relationship.infrastructure.persistence.UserRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteRelationshipUseCase {
    @Autowired
    private UserRelationshipRepository userRelationshipRepository;

    @Autowired
    private VerifyUserUseCase verifyUserUseCase;

    public void deleteAllByUserId(UUID id){
        if (!verifyUserUseCase.existsById(id))
            throw new NotFoundException("User not found");

        userRelationshipRepository.deleteAllByUserId(id);
    }
}
