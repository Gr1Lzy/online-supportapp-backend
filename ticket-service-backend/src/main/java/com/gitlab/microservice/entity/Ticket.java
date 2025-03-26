package com.gitlab.microservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "tickets")
public class Ticket extends AbstractEntity {

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

  @Field("comments")
  private List<Comment> comments;

  @Field("logs")
  private List<TicketLog> logs;

  public Ticket init() {
    this.status = TicketStatus.OPENED;
    return this;
  }
}
