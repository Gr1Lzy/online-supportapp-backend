package com.gitlab.ticketservice.util;

import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.entity.TicketStatus;
import com.gitlab.ticketservice.exception.TicketMessageException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TicketSchedulerUtil {

  private static final String TICKET_CLOSED = "Ticket is closed";

  public static void checkTicketStatus(Ticket ticket) {
    TicketStatus currentTicketStatus = ticket.getStatus();
    Boolean isOlderThanTwoWeeks = ticket.getIsTicketOlderThanTwoWeeks();

    if (currentTicketStatus == TicketStatus.CLOSED ||
        (isOlderThanTwoWeeks != null && isOlderThanTwoWeeks)) {
      throw new TicketMessageException(TICKET_CLOSED);
    }
  }
}