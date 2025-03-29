package com.gitlab.microservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

  @Schema(example = "newexample@mail.com")
  @JsonProperty(value = "email")
  private String email;

  @Schema(example = "newFirstName")
  @JsonProperty(value = "first_name")
  private String firstName;

  @Schema(example = "newLastName")
  @JsonProperty(value = "last_name")
  private String lastName;
}
