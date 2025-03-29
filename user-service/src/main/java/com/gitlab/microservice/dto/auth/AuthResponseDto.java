package com.gitlab.microservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {

  @JsonProperty(value = "token_type")
  private String tokenType;

  @JsonProperty(value = "access_token")
  private String accessToken;

  @JsonProperty(value = "refresh_token")
  private String refreshToken;

  @JsonProperty(value = "expires_in")
  private Long expiresIn;
}
