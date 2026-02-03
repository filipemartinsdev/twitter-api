package com.api.twitter.tweet.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record TweetRequest (
        UUID parentId,
        @NotBlank(message = "Tweet content can't be blank") String content
){}
