package com.gitlab.microservice.exception.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException exception) throws IOException {

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");

    Map<String, Object> errorResponse = Map.of(
        "status", HttpStatus.UNAUTHORIZED.value(),
        "error", "Unauthorized",
        "message", exception.getMessage()
    );

    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}

