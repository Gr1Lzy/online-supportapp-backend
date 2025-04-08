package com.gitlab.ticketservice.util;

import com.gitlab.ticketservice.entity.Log;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoggingService {

  private final TicketRepository ticketRepository;

  @Async
  @EventListener
  public void handleTicketEvent(TicketEvent event) {

    Ticket ticket = ticketRepository.findById(event.getTicketId())
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    Log log = Log.builder()
        .action(event.getAction())
        .actionById(event.getUserId())
        .actionDate(LocalDateTime.now())
        .build();

    ticket.getLogs().add(log);

    ticketRepository.save(ticket);
  }
}
