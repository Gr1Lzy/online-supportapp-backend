package com.gitlab.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDto {

  @Schema(example = "username")
  @NotBlank(message = "Username is required")
  @JsonProperty(value = "username")
  private String username;

  @Schema(example = "example@example.com")
  @NotBlank(message = "Email is required")
  @Email(message = "Email is not valid")
  @JsonProperty(value = "email")
  private String email;

  @Schema(example = "password")
  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @JsonProperty(value = "password")
  private String password;

  @Schema(example = "firstName")
  @Size(min = 2, message = "First name must be at least 2 characters long")
  @JsonProperty(value = "first_name")
  private String firstName;

  @Schema(example = "lastName")
  @Size(min = 2, message = "Last name must be at least 2 characters long")
  @JsonProperty(value = "last_name")
  private String lastName;
}
