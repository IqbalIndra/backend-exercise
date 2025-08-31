package com.example._9.public_api_service.controller;

import com.example._9.public_api_service.exception.ValidationErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        result.put("result", false);
        result.put("errors",errors);
        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<Map<String, Object>> handleCustomValidationExceptions(ValidationErrorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("result",false,"errors", ex.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) {
        // Return a custom error response based on the HTTP status code
        if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<HashMap<String,Object>> typeRef
                    = new TypeReference<HashMap<String,Object>>() {};
            HashMap<String,Object> o;
            try {
                 o = objectMapper.readValue(ex.getResponseBodyAsString(), typeRef);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new ResponseEntity<>(o, HttpStatus.BAD_REQUEST);
        }
        // Handle other 4xx status codes as needed
        return new ResponseEntity<>("Client Error: " + ex.getMessage(), ex.getStatusCode());
    }

}
