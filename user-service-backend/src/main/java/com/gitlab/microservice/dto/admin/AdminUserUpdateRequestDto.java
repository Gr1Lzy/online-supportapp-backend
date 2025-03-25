package com.gitlab.microservice.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminUserUpdateRequestDto {

  @Schema(example = "test@example.com")
  @Email(message = "Invalid email format")
  @JsonProperty("email")
  private String email;

  @Schema(example = "12345678")
  @Size(min = 8, message = "Password must be more than 8 characters")
  @JsonProperty("password")
  private String password;

  @Schema(example = "test")
  @Size(max = 50, message = "First name must not exceed 50 characters")
  @JsonProperty("first_name")
  private String firstName;

  @Schema(example = "test")
  @Size(max = 50, message = "Last name must not exceed 50 characters")
  @JsonProperty("last_name")
  private String lastName;

  @Schema(description = "ROLE_ADMIN | ROLE_USER | ROLE_SUPPORT", example = "ROLE_USER")
  @JsonProperty("role")
  private String role;
}