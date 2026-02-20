package com.api.twitter.tweet.application.dto;

import java.io.Serializable;
import java.util.UUID;

public record TweetUserResponse (
    UUID id,
    String username
) implements Serializable {}
