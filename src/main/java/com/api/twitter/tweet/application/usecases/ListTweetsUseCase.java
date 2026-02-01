package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Component
public class ListTweetsUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    public PagedResponse<TweetResponse> listAll(Pageable pageable) {
        Page<Tweet> page = tweetRepository.findAllTweets(pageable);

        return PagedResponse.<TweetResponse>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .isLast(page.isLast())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent().stream()
                        .map(tweet -> tweetMapper.toResponse(tweet))
                        .toList()
                )
                .build();
    }

    public PagedResponse<TweetResponse> listAllTweetsByUserId(UUID userId, Pageable pageable) {
        Page<Tweet> page = tweetRepository.findAllTweetsByUserId(userId, pageable);

        return PagedResponse.<TweetResponse>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .isLast(page.isLast())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent().stream()
                        .map(tweet -> tweetMapper.toResponse(tweet))
                        .toList()
                )
                .build();
    }

    public PagedResponse<TweetResponse> listAllCommentsByTweetId(UUID tweetId, Pageable pageable) {
        Page<Tweet> page = tweetRepository.findAllByParentId(tweetId, pageable);

        return PagedResponse.<TweetResponse>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .isLast(page.isLast())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent().stream()
                        .map(tweet -> tweetMapper.toResponse(tweet))
                        .toList()
                )
                .build();
    }
}
