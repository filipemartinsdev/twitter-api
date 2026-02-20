package com.api.twitter.tweet.application.usecases;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

@Caching(evict = {
        @CacheEvict(value = "tweetsByPageSizeSort", allEntries = true),
        @CacheEvict(value = "tweetsByUserIdPageSizeSort", allEntries = true),
        @CacheEvict(value = "tweetById", key = "#tweetId.toString()"),
})
public class UpdateTweetUseCase {
}
