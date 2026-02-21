package com.api.twitter.user.relationship.domain;

import com.api.twitter.user.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class UserRelationshipId implements Serializable {
    private UserProfile follower;
    private UserProfile following;
}
