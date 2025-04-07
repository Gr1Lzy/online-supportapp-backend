package com.gitlab.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {

  @Id
  private String id;

  @Field("text")
  private String text;

  @Field("author_id")
  private String authorId;

  @Field("created_date")
  private LocalDateTime createdDate;
}
