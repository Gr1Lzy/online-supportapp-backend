package com.gitlab.microservice.excetpion;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleRemoteNotFound(EntityNotFoundException exception) {
    Map<String, Object> error = Map.of(
        "status", HttpStatus.NOT_FOUND.value(),
        "error", exception.getClass().getSimpleName(),
        "message", exception.getMessage()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
    Map<String, Object> errorResponse = Map.of(
        "status", HttpStatus.BAD_REQUEST.value(),
        "error", exception.getClass().getSimpleName(),
        "message", getErrors(exception)
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private List<String> getErrors(Errors exception) {
    return exception.getAllErrors().stream()
        .map(MessageSourceResolvable::getDefaultMessage)
        .toList();
  }
}
