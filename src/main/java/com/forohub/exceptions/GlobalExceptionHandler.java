package com.forohub.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> processValidationException(ResourceNotFoundException e) {
        Map<String, String> exceptionMessage = new HashMap<>();

        exceptionMessage.put("message", "Resource not found: " + e.getMessage());

        return exceptionMessage;
    }


    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> processValidationException(MethodArgumentNotValidException e) {
        Map<String, String> exceptionMessage = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            exceptionMessage.put(fieldName, errorMessage);
        });

        return exceptionMessage;
    }


    @ExceptionHandler({ BadRequestException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> processBadRequest(BadRequestException e) {
        Map<String, String> exceptionMessage = new HashMap<>();

        exceptionMessage.put("message", e.getMessage());

        return exceptionMessage;
    }


    @ExceptionHandler({ JsonParseException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> processJsonParseException(JsonParseException e) {
        Map<String, String> exceptionMessage = new HashMap<>();

        exceptionMessage.put("path", String.valueOf(e.getClass()));
        exceptionMessage.put("message", e.getMessage());

        return exceptionMessage;
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        String errorMessage = "The role cannot be empty or is incorrect. Only values allowed: ADMIN, STUDENT, TEACHER";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
