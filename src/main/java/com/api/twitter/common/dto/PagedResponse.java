package com.api.twitter.common.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResponse<T>(
        Integer size,
        Integer page,
        Integer totalPages,
        Long totalElements,
        boolean isLast,
        List<T> content
){
}
