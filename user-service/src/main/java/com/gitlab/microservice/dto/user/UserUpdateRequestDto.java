package com.gitlab.microservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

  @Schema(example = "newexample@example.com")
  @Email(message = "Email is not valid")
  @JsonProperty(value = "email")
  private String email;

  @Schema(example = "newFirstName")
  @Size(min = 2, message = "First name must be at least 2 characters long")
  @JsonProperty(value = "first_name")
  private String firstName;

  @Schema(example = "newLastName")
  @Size(min = 2, message = "Last name must be at least 2 characters long")
  @JsonProperty(value = "last_name")
  private String lastName;
}
