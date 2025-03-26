package com.gitlab.microservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
