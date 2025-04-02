package com.gitlab.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.ticketservice.entity.TicketStatus;
import com.gitlab.ticketservice.util.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusRequestDto {

  @Schema(description = "OPENED | IN_PROGRESS | CLOSED", example = "CLOSED")
  @NotBlank(message = "Status is required")
  @EnumValidator(enumClass = TicketStatus.class,
      message = "Status must be one of: OPENED, IN_PROGRESS, CLOSED")
  @JsonProperty("status")
  private String status;
}
