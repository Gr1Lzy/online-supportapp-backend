package com.gitlab.ticketservice.util;

import com.gitlab.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketScheduler {

  private final TicketRepository ticketRepository;

  @Autowired
  public TicketScheduler(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void closeOldTickets() {
    LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);

    ticketRepository.setTicketOlderThanTwoWeeks(twoWeeksAgo);
  }
}