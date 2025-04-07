package com.gitlab.ticketservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private static final String STATUS = "status";
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private static final String TIMESTAMP = "timestamp";
  private static final String PATH = "path";

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException exception,
                                                                  HttpServletRequest request) {

    Map<String, Object> error = createErrorResponse(
        HttpStatus.NOT_FOUND,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        request
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(TicketSendMessageException.class)
  public ResponseEntity<Map<String, Object>> handleTicketSendMessageException(TicketSendMessageException exception,
                                                                              HttpServletRequest request) {

    Map<String, Object> error = createErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        request
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception,
                                                                   HttpServletRequest request) {

    Map<String, Object> errorResponse = createErrorResponse(
        HttpStatus.BAD_REQUEST,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        request
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException exception,
                                                                HttpServletRequest request) {

    Map<String, Object> errorResponse = createErrorResponse(
        HttpStatus.SERVICE_UNAVAILABLE,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        request
    );

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
  }

  private Map<String, Object> createErrorResponse(HttpStatus status, String error, Object message,
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

  private List<String> getErrors(Errors exception) {
    return exception.getAllErrors().stream()
        .map(MessageSourceResolvable::getDefaultMessage)
        .toList();
  }
}
