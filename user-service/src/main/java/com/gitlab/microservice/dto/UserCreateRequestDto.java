package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDto {

  @Schema(example = "example@mail.com")
  @NotBlank
  @Email
  @JsonProperty(value = "email")
  private String email;

  @Schema(example = "username")
  @NotBlank
  @JsonProperty(value = "username")
  private String username;

  @Schema(example = "password")
  @NotBlank
  @Size(min = 8)
  @JsonProperty(value = "password")
  private String password;

  @Schema(example = "firstName")
  @Size(min = 2)
  @JsonProperty(value = "first_name")
  private String firstName;

  @Schema(example = "lastName")
  @Size(min = 2)
  @JsonProperty(value = "last_name")
  private String lastName;
}
