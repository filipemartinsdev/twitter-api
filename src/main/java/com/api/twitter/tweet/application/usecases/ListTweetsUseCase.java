package com.api.twitter.tweet.application.usecases;

import com.api.twitter.common.dto.PagedResponse;
import com.api.twitter.common.exception.NotFoundException;
import com.api.twitter.tweet.application.dto.TweetAndCounts;
import com.api.twitter.tweet.application.dto.TweetResponse;
import com.api.twitter.tweet.application.exceptions.TweetNotFound;
import com.api.twitter.tweet.application.mappers.TweetMapper;
import com.api.twitter.tweet.domain.Tweet;
import com.api.twitter.tweet.infrastructure.persistence.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListTweetsUseCase {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    public TweetResponse getById(UUID tweetId){
        return tweetMapper.toResponse(
                tweetRepository.findTweetAndCountsById(tweetId)
                        .orElseThrow(() -> new TweetNotFound())
        );
    }

    @Cacheable(value = "tweetPages", key = "#pageable.pageNumber", condition="#page!=null")
    public PagedResponse<TweetResponse> listAll(Pageable pageable) {
        Page<TweetAndCounts> page = tweetRepository.findAllTweetAndCounts(pageable);

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

    @Cacheable(value = "userTweetPages", key = "#userId.toString() + '_' + #pageable.pageNumber", condition="#page!=null")
    public PagedResponse<TweetResponse> listAllTweetsByUserId(UUID userId, Pageable pageable) {
        Page<TweetAndCounts> page = tweetRepository.findAllTweetAndCountsByUserId(userId, pageable);

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
        Page<TweetAndCounts> page = tweetRepository.findAllTweetAndCountsByParentId(tweetId, pageable);

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
