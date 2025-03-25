package com.gitlab.microservice.dto.admin;

import lombok.Data;

@Data
public class AdminUserResponseDto {

  private String id;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private String role;
}
