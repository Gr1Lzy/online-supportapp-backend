package com.gitlab.microservice.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.microservice.exception.standard.AuthenticationException;
import com.gitlab.microservice.exception.standard.EntityExistException;
import com.gitlab.microservice.exception.standard.EntityNotFoundException;
import com.gitlab.microservice.exception.standard.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";

  @ExceptionHandler(value = {
      EntityExistException.class,
      InvalidTokenException.class,
      EntityNotFoundException.class
  })
  public ResponseEntity<Map<String, Object>> handleEntityExist(Exception exception) {
    String rawMessage = exception.getMessage();
    String errorMessage = rawMessage;

    try {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> json = mapper.readValue(rawMessage, new TypeReference<>() {});
      errorMessage = json.getOrDefault("errorMessage", rawMessage);
    } catch (Exception e) {
      log.info("Error parsing message: {}", rawMessage);
    }

    Map<String, Object> error = Map.of(
        STATUS, HttpStatus.BAD_REQUEST.value(),
        ERROR, exception.getClass().getSimpleName(),
        MESSAGE, errorMessage
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
    Map<String, Object> errorResponse = Map.of(
        STATUS, HttpStatus.BAD_REQUEST.value(),
        ERROR, exception.getClass().getSimpleName(),
        MESSAGE, getErrors(exception)
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private List<String> getErrors(Errors exception) {
    return exception.getAllErrors().stream()
        .map(MessageSourceResolvable::getDefaultMessage)
        .toList();
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
    Map<String, Object> errorResponse = Map.of(
        STATUS, HttpStatus.UNAUTHORIZED.value(),
        ERROR, ex.getClass().getSimpleName(),
        MESSAGE, ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }
}
