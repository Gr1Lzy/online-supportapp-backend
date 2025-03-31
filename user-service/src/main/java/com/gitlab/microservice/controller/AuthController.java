package com.gitlab.microservice.controller;

import com.gitlab.microservice.dto.auth.AuthRequestDto;
import com.gitlab.microservice.dto.auth.AuthResponseDto;
import com.gitlab.microservice.dto.auth.RefreshRequestDto;
import com.gitlab.microservice.dto.user.UserCreateRequestDto;
import com.gitlab.microservice.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Authentication Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "Register user")
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid UserCreateRequestDto requestDto) {
    authService.register(requestDto);
    return ResponseEntity.status(CREATED).build();
  }

  @Operation(summary = "Login user")
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto requestDto) {
    return ResponseEntity.ok(authService.login(requestDto));
  }

  @Operation(summary = "Refresh token")
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDto> refresh(@RequestBody @Valid RefreshRequestDto requestDto) {
    return ResponseEntity.ok(authService.refresh(requestDto));
  }
}
