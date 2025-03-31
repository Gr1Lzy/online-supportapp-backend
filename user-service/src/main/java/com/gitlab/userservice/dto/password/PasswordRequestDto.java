package com.gitlab.userservice.dto.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordRequestDto {

  @Schema(example = "newpassword")
  @NotBlank
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @JsonProperty(value = "new_password")
  private String newPassword;
}
