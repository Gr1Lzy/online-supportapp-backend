package com.gitlab.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "tickets")
public class Ticket {

  @Id
  private String id;

  @Field("title")
  private String title;

  @Field("description")
  private String description;

  @Field("assignee_id")
  private String assigneeId;

  @Field("reporter_id")
  private String reporterId;

  @Field("status")
  private TicketStatus status;

  @DBRef
  private List<Comment> comments;

  @Field("logs")
  private List<Log> logs;

  @CreatedDate
  @Field("created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Field("updated_at")
  private LocalDateTime updatedAt;

  public Ticket init() {
    status = TicketStatus.OPENED;
    comments = new ArrayList<>();
    logs = new ArrayList<>();
    return this;
  }
}
