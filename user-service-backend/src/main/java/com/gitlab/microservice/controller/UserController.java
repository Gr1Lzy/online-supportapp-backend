package com.gitlab.microservice.controller;


import com.gitlab.microservice.dto.user.UserRequestDto;
import com.gitlab.microservice.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<String> create(@RequestBody @Valid UserRequestDto requestDto) {
    userService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
