package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponseDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("email")
  private String email;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;
}
