package com.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int status,
        String message,
        T data,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), message, data, LocalDateTime.now());
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, null, LocalDateTime.now());
    }

    public static ApiResponse<Void> noContent(String message) {
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), message, null, LocalDateTime.now());
    }
}
