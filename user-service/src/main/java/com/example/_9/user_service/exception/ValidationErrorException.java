package com.example._9.user_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationErrorException extends RuntimeException {
    private String message;
}
