package com.gitlab.microservice.controller;

import com.gitlab.microservice.dto.admin.AdminUserCreateRequestDto;
import com.gitlab.microservice.dto.admin.AdminUserResponseDto;
import com.gitlab.microservice.dto.admin.AdminUserUpdateRequestDto;
import com.gitlab.microservice.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller for admin")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

  private final AdminUserService adminUserService;

  @Operation(summary = "Get all users")
  @GetMapping
  public ResponseEntity<Page<AdminUserResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(adminUserService.findAll(page, size));
  }

  @Operation(summary = "Get user by Id")
  @GetMapping("/{id}")
  public ResponseEntity<AdminUserResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(adminUserService.findById(id));
  }

  @Operation(summary = "Create user")
  @PostMapping
  public ResponseEntity<String> create(@RequestBody @Valid AdminUserCreateRequestDto requestDto) {
    adminUserService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Update user")
  @PatchMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable String id,
                                     @RequestBody @Valid AdminUserUpdateRequestDto requestDto) {
    adminUserService.update(id, requestDto);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Delete user by Id")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    adminUserService.delete(id);
    return ResponseEntity.ok().build();
  }
}
