package com.gitlab.ticketservice.util;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import static com.gitlab.ticketservice.util.UserUtil.getCurrentUserId;


@Getter
public class TicketEvent extends ApplicationEvent {
  private final String ticketId;
  private final String action;
  private final String userId;

  public TicketEvent(Object source, String ticketId, String action) {
    super(source);
    this.ticketId = ticketId;
    this.action = action;
    this.userId = getCurrentUserId();
  }
}