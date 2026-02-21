package com.api.twitter.user.relationship.domain;

import com.api.twitter.user.domain.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name = "user_relationship")
@IdClass(UserRelationshipId.class)
@Data
@AllArgsConstructor @NoArgsConstructor
public class UserRelationship {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private UserProfile follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private UserProfile following;

    @Column(name = "created_at")
    private LocalDateTime timestamp;
}
