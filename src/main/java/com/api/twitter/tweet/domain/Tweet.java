package com.api.twitter.tweet.domain;

import com.api.twitter.user.domain.UserProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Tweet {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "parent_id")
    private UUID parentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @NotEmpty
    private String content;

    @NotNull
    private LocalDateTime createdAt;
}
