package com.gitlab.ticketservice.util;

import com.gitlab.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TicketScheduler {

  private final TicketRepository ticketRepository;

  @Autowired
  public TicketScheduler(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void closeOldTickets() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.WEEK_OF_YEAR, -2);
    Date twoWeeksAgo = calendar.getTime();

    Date currentDate = new Date();

    ticketRepository.setStatusClosedOnTicketWhereCreatedAtMoreThanTwoWeeks(twoWeeksAgo, currentDate);
  }
}
