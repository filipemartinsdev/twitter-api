package com.api.twitter.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Table(name = "users")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    private String username;

    @Email
    private String email;

    @NotEmpty
    private String password;

    @Column(name = "followers_count")
    private Long followersCount;

    @Column(name = "following_count")
    private Long followingCount;

    @Column(name = "tweets_count")
    private Long tweetsCount;
}
