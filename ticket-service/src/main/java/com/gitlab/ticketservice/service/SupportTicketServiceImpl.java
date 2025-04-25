package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.StatusRequestDto;
import com.gitlab.ticketservice.dto.user.UserIdRequestDto;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.entity.TicketStatus;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.TicketEvent;
import com.gitlab.ticketservice.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.gitlab.ticketservice.util.TicketSchedulerUtil.checkTicketStatus;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

  private final TicketRepository ticketRepository;
  private final UserServiceHelper userServiceHelper;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void assignOnUser(String ticketId, UserIdRequestDto requestDto) {
    Ticket ticket = findByIdOptional(ticketId);
    String userId = requestDto.getUserId();

    checkTicketStatus(ticket);

    userServiceHelper.safeFindById(userId);
    ticket.setAssigneeId(userId);

    ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticket.getId(), "Assigned Ticket")
    );
  }

  @Override
  public void unassignUser(String ticketId) {
    Ticket ticket = findByIdOptional(ticketId);

    checkTicketStatus(ticket);

    ticket.setAssigneeId(null);
    ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticket.getId(), "Unassigned Ticket")
    );
  }

  @Override
  public void updateStatus(String ticketId, StatusRequestDto status) {
    Ticket ticket = findByIdOptional(ticketId);

    checkTicketStatus(ticket);

    ticket.setStatus(TicketStatus.valueOf(status.getStatus()));
    ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticket.getId(), "Updated Ticket Status")
    );
  }

  private Ticket findByIdOptional(String ticketId) {
    return ticketRepository.findById(ticketId)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
  }
}
