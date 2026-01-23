package com.api.twitter.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        String status,
        T data,
        String message
){
    public static ApiResponse<Void> success(){
        return new ApiResponse<Void>("success", null, null);
    }
}
