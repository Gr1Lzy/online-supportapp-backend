package com.gitlab.microservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketLog {

  @Field("action")
  private String action;

  @Field("action_by_id")
  private String actionById;

  @Field("action_date")
  private LocalDateTime actionDate;
}
