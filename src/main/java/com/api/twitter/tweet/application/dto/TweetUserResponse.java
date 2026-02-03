package com.api.twitter.tweet.application.dto;

import java.util.UUID;

public record TweetUserResponse (
    UUID id,
    String username
){}
