package com.gitlab.microservice.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  @ExceptionHandler(EntityExistException.class)
  public ResponseEntity<Map<String, Object>> handleEntityExist(EntityExistException exception) {
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
        "status", HttpStatus.BAD_REQUEST.value(),
        "error", exception.getClass().getSimpleName(),
        "message", errorMessage
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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
