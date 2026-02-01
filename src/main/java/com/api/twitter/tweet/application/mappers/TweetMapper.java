package com.api.twitter.tweet.application.mappers;

import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.dto.TweetUserResponse;
import com.api.twitter.tweet.domain.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {
    public TweetResponse toResponse(Tweet tweet){
        return new TweetResponse(
                tweet.getId(),
                tweet.getParentId(),
                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getLikesCount(),
                tweet.getCommentsCount(),
                new TweetUserResponse(
                        tweet.getUser().getId(),
                        tweet.getUser().getUsername()
                )
        );
    }
}
