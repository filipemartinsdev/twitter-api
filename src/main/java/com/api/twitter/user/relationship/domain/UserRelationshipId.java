package com.api.twitter.user.relationship.domain;

import com.api.twitter.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class UserRelationshipId implements Serializable {
    private User follower;
    private User following;
}
