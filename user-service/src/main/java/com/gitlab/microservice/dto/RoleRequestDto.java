package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.microservice.entity.UserRole;
import com.gitlab.microservice.util.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequestDto {

  @Schema(description = "ROLE_ADMIN | ROLE_USER | ROLE_SUPPORT", example = "ROLE_USER")
  @NotBlank(message = "Role is required")
  @EnumValidator(enumClass = UserRole.class, message = "Role must be one of: ROLE_ADMIN, ROLE_USER, ROLE_SUPPORT")
  @JsonProperty("role")
  private String role;
}
