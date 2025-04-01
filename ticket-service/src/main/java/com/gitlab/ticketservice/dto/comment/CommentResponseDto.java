package com.gitlab.ticketservice.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitlab.ticketservice.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class CommentResponseDto {

  @JsonProperty("text")
  private String text;

  @JsonProperty("author")
  private UserResponseDto author;

  @JsonProperty("created_date")
  private String createdDate;
}
