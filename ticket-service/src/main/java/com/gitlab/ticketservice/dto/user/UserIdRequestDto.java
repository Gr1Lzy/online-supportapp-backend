package com.gitlab.ticketservice.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserIdRequestDto {

  @NotBlank(message = "User id is required")
  @JsonProperty("user_id")
  private String userId;
}
