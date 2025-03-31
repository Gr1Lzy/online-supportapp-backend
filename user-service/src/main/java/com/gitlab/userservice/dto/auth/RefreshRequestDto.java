package com.gitlab.userservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshRequestDto {

  @JsonProperty(value = "refresh_token")
  @NotBlank(message = "Refresh token is required")
  private String refreshToken;
}
