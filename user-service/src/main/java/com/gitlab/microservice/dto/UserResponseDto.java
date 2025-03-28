package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponseDto {

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
}
