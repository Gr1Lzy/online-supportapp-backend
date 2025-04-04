package com.gitlab.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketRequestDto {

  @Schema(example = "Ticket title")
  @NotBlank(message = "Title is required")
  @JsonProperty("title")
  private String title;

  @Schema(example = "Ticket description")
  @JsonProperty("description")
  private String description;

  @Schema(example = "1")
  @JsonProperty("assignee_id")
  private String assigneeId;
}
