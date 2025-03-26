package com.gitlab.microservice.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminUserResponseDto {

  @JsonProperty("id")
  private Object id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("role")
  private String role;
}
