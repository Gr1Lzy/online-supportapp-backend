package com.gitlab.ticketservice.dto.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.ticketservice.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class LogResponseDto {

  @JsonProperty("action")
  private String action;

  @JsonProperty("action_by")
  private UserResponseDto actionBy;

  @JsonProperty("action_date")
  private String actionDate;
}
