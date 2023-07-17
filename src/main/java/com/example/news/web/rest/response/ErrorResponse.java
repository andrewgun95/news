package com.example.news.web.rest.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {

    private final HttpStatus status;

    private final String message;

    private final int errorCode;

    private final Date timestamp;

    public ErrorResponse(HttpStatus status, String message, int errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = new Date();
    }
}
