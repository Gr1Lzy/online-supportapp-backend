package com.gitlab.userservice.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.userservice.exception.standard.AuthenticationException;
import com.gitlab.userservice.exception.standard.EntityExistException;
import com.gitlab.userservice.exception.standard.EntityNotFoundException;
import com.gitlab.userservice.exception.standard.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private static final String TIMESTAMP = "timestamp";
  private static final String PATH = "path";

  private final ObjectMapper objectMapper;

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException exception,
                                                                  HttpServletRequest request) {

    String errorMessage = extractErrorMessage(exception.getMessage());

    Map<String, Object> error = createErrorResponse(
        HttpStatus.NOT_FOUND,
        exception.getClass().getSimpleName(),
        errorMessage,
        request
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EntityExistException.class)
  public ResponseEntity<Map<String, Object>> handleEntityExists(EntityExistException exception,
                                                                HttpServletRequest request) {

    String errorMessage = extractErrorMessage(exception.getMessage());

    Map<String, Object> error = createErrorResponse(
        HttpStatus.CONFLICT,
        exception.getClass().getSimpleName(),
        errorMessage,
        request
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidToken(InvalidTokenException exception,
                                                                HttpServletRequest request) {

    String errorMessage = extractErrorMessage(exception.getMessage());

    Map<String, Object> error = createErrorResponse(
        HttpStatus.UNAUTHORIZED,
        exception.getClass().getSimpleName(),
        errorMessage,
        request
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception,
                                                              HttpServletRequest request) {

    List<String> errors = getErrors(exception);

    Map<String, Object> errorResponse = createErrorResponse(
        HttpStatus.BAD_REQUEST,
        exception.getClass().getSimpleName(),
        errors,
        request
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException exception,
                                                                           HttpServletRequest request) {

    Map<String, Object> errorResponse = createErrorResponse(
        HttpStatus.UNAUTHORIZED,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        request
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException exception,
                                                                HttpServletRequest request) {

    Map<String, Object> errorResponse = createErrorResponse(
        HttpStatus.FORBIDDEN,
        exception.getClass().getSimpleName(),
        "You don't have permission to access this resource",
        request
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
  }

  private Map<String, Object> createErrorResponse(HttpStatus status, String error,
                                                  Object message,
                                                  HttpServletRequest request) {

    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put(STATUS, status.value());
    errorResponse.put(ERROR, error);
    errorResponse.put(MESSAGE, message);
    errorResponse.put(TIMESTAMP, LocalDateTime.now().toString());

    if (request != null) {
      errorResponse.put(PATH, request.getRequestURI());
    }

    return errorResponse;
  }

  private String extractErrorMessage(String rawMessage) {
    if (rawMessage == null || !rawMessage.startsWith("{")) {
      return rawMessage;
    }

    try {
      Map<String, String> json = objectMapper.readValue(rawMessage, new TypeReference<>() {});
      return json.getOrDefault("errorMessage",
          json.getOrDefault(MESSAGE, rawMessage));
    } catch (Exception e) {
      return rawMessage;
    }
  }

  private List<String> getErrors(Errors exception) {
    return exception.getAllErrors().stream()
        .map(MessageSourceResolvable::getDefaultMessage)
        .toList();
  }
}