package com.gitlab.microservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.microservice.dto.comment.CommentResponseDto;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.entity.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
public class TicketResponseDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("assignee")
  private UserResponseDto assignee;

  @JsonProperty("reporter")
  private UserResponseDto reporter;

  @JsonProperty("status")
  private TicketStatus status;

  @JsonProperty("comments")
  private List<CommentResponseDto> comments;

  @JsonProperty("logs")
  private List<TicketLogResponseDto> logs;

  @JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("updated_at")
  private String updatedAt;
}
