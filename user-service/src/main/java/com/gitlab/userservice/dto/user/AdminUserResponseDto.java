package com.gitlab.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AdminUserResponseDto {

  @JsonProperty(value = "id")
  private String id;

  @JsonProperty(value = "username")
  private String username;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "first_name")
  private String firstName;

  @JsonProperty(value = "last_name")
  private String lastName;

  @JsonProperty(value = "roles")
  private List<String> roles;
}
