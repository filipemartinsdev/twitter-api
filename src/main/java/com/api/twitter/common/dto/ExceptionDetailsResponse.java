package com.api.twitter.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionDetailsResponse {
    private Integer statusCode;
    private String details;
    private LocalDateTime timestamp;

    private ExceptionDetailsResponse(){}

    public ExceptionDetailsResponse(Integer statusCode, String details){
        this.statusCode = statusCode;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
