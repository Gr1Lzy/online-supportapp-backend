package com.gitlab.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

import static com.gitlab.ticketservice.util.UserUtil.getCurrentUserId;

@Getter
@Setter
@Document(collection = "comments")
public class Comment {

  @Id
  private String id;

  @Field("ticket_id")
  private String ticketId;

  @Field("text")
  private String text;

  @Field("author_id")
  private String authorId;

  @Field("created_date")
  private LocalDateTime createdDate;

  public Comment init(String ticketId) {
    this.ticketId = ticketId;
    authorId = getCurrentUserId();
    createdDate = LocalDateTime.now();
    return this;
  }
}
