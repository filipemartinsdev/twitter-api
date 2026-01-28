package com.api.twitter.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        String status,
        T data,
        String message
){
    public static ApiResponse<Void> success(){
        return new ApiResponse<>("success", null, null);
    }

    public static ApiResponse<Void> fail(String message){
        return new ApiResponse<>("fail", null, message);
    }

    public static <T> ApiResponse<T> fail(T data){
        return new ApiResponse<>("fail", data, null);
    }

    public static ApiResponse<Void> error(String message){
        return new ApiResponse<>("error", null, message);
    }

    public static ApiResponse<Void> error(){
        return new ApiResponse<>("error", null, null);
    }
}
