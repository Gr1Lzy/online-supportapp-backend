package com.gitlab.microservice.controller;

import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getCurrentUser() {
    return ResponseEntity.ok(userService.getCurrentUser());
  }
}
