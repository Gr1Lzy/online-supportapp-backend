package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TicketLogResponseDto {

  @JsonProperty("action")
  private String action;

  @JsonProperty("action_by")
  private UserResponseDto actionBy;

  @JsonProperty("action_date")
  private String actionDate;
}
