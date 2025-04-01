package com.gitlab.userservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

  @Schema(example = "username")
  @NotBlank(message = "Username is required")
  @JsonProperty(value = "username")
  private String username;

  @Schema(example = "password")
  @NotBlank(message = "Password is required")
  @JsonProperty(value = "password")
  private String password;
}
