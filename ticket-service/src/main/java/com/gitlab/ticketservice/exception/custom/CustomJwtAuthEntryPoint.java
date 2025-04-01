package com.gitlab.ticketservice.exception.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");

    Map<String, Object> errorResponse = Map.of(
        "path", request.getRequestURI(),
        "status", HttpStatus.UNAUTHORIZED.value(),
        "error", "Unauthorized",
        "message", authException.getMessage() != null
            ? authException.getMessage()
            : "Authentication failed: Invalid token or missing credentials",
        "timestamp", LocalDateTime.now().toString()
    );

    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
