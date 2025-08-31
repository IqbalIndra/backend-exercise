package com.example._9.public_api_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationErrorException extends RuntimeException {
    private String message;
}
