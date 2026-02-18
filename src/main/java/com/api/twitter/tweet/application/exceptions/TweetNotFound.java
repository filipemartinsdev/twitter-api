package com.api.twitter.tweet.application.exceptions;

public class TweetNotFound extends RuntimeException {
    public TweetNotFound(String message) {
        super(message);
    }

    public TweetNotFound(){
        super();
    }
}
