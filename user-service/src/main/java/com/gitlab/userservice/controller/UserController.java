package com.gitlab.userservice.controller;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.password.PasswordRequestDto;
import com.gitlab.userservice.dto.user.UserResponseDto;
import com.gitlab.userservice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Get current user")
  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> getCurrentUser() {
    return ResponseEntity.ok(userService.getCurrentUser());
  }

  @Operation(summary = "Find user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @Operation(summary = "Find all users")
  @GetMapping
  public ResponseEntity<PageDto<UserResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(userService.findAll(page, size));
  }

  @Operation(summary = "Update password")
  @PatchMapping("/update-password")
  public ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordRequestDto requestDto) {
    userService.updatePassword(requestDto);
    return ResponseEntity.ok().build();
  }
}
