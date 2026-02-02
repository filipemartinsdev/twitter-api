package com.api.twitter.tweet.domain;

import com.api.twitter.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    private User user;

    @NotEmpty
    private String content;

    @NotNull @Min(0)
    private Long likesCount;

    @NotNull @Min(0)
    private Long commentsCount;

    @NotNull @Min(0)
    private Long viewsCount;

    @NotNull
    private LocalDateTime createdAt;

    public static Tweet newDefault(){
        return new Tweet(
                null,
                null,
                null,
                null,
                0L,
                0L,
                0L,
                LocalDateTime.now()
        );
    }

    public void incrementLikesCount(){
        this.likesCount++;
    }

    public void decrementLikesCount(){
        this.likesCount--;
    }

    public void incrementCommentsCount(){
        this.commentsCount++;
    }

    public void decrementCommentsCount(){
        this.commentsCount--;
    }

    public void incrementViewsCount(){
        this.viewsCount++;
    }

    public void decrementViewsCount(){
        this.viewsCount--;
    }
}
