package com.api.twitter.user.relationship.domain;

import com.api.twitter.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name = "user_relationship")
@IdClass(UserRelationship.class)
@Data
@AllArgsConstructor @NoArgsConstructor
public class UserRelationship {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    @Column(name = "created_at")
    private LocalDateTime timestamp;
}
