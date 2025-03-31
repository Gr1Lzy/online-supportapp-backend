package com.gitlab.userservice.controller;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.role.RoleRequestDto;
import com.gitlab.userservice.dto.user.AdminUserResponseDto;
import com.gitlab.userservice.dto.user.UserUpdateRequestDto;
import com.gitlab.userservice.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin User Controller")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

  private final AdminUserService adminUserService;

  @Operation(summary = "Update user by id")
  @PatchMapping("/{id}")
  public ResponseEntity<String> update(@PathVariable String id, @RequestBody @Valid UserUpdateRequestDto requestDto) {
    adminUserService.update(id, requestDto);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Delete user by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    adminUserService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Find all users with Pageable")
  @GetMapping
  public ResponseEntity<PageDto<AdminUserResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "10") Integer size) {
    return ResponseEntity.ok(adminUserService.findAll(page, size));
  }

  @Operation(summary = "Find user by id")
  @GetMapping("/{id}")
  public ResponseEntity<AdminUserResponseDto> findById(@PathVariable String id) {
    return ResponseEntity.ok(adminUserService.findById(id));
  }

  @Operation(summary = "Assign role to user")
  @PatchMapping("/{id}/roles")
  public ResponseEntity<String> assignRole(@PathVariable String id, @RequestBody @Valid RoleRequestDto requestDto) {
    adminUserService.assignRole(id, requestDto);
    return ResponseEntity.ok().build();
  }
}
