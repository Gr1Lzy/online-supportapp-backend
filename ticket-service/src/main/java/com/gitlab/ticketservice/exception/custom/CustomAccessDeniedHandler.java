package com.gitlab.ticketservice.exception.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException exception) throws IOException {

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");

    Map<String, Object> errorResponse = Map.of(
        "path", request.getRequestURI(),
        "status", HttpStatus.FORBIDDEN.value(),
        "error", "Forbidden",
        "message", exception.getMessage() != null
            ? exception.getMessage()
            : "Access denied: Insufficient permissions to access this resource",
        "timestamp", LocalDateTime.now().toString()
    );

    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}


