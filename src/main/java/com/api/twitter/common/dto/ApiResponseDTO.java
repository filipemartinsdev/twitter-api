package com.api.twitter.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponseDTO<T> (
        String status,
        T data,
        String message
){
    public static ApiResponseDTO<Void> success(){
        return new ApiResponseDTO<>("success", null, null);
    }

    public static <T> ApiResponseDTO<T> success(T data){
        return new ApiResponseDTO<T>("success", data, null);
    }

    public static ApiResponseDTO<Void> fail(String message){
        return new ApiResponseDTO<>("fail", null, message);
    }

    public static <T> ApiResponseDTO<T> fail(T data){
        return new ApiResponseDTO<>("fail", data, null);
    }

    public static ApiResponseDTO<Void> error(String message){
        return new ApiResponseDTO<>("error", null, message);
    }

    public static ApiResponseDTO<Void> error(){
        return new ApiResponseDTO<>("error", null, null);
    }
}
