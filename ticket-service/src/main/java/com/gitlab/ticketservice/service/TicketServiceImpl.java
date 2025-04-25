package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.mapper.context.UserMappingContext;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.TicketEvent;
import com.gitlab.ticketservice.util.UserServiceHelper;
import com.gitlab.ticketservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.gitlab.ticketservice.mapper.TicketMapper.TICKET_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private static final Sort DEFAULT_SORT = Sort.by("createdAt").descending();
  private static final String TICKET_ASSIGNED_TO_ME = "Ticket assigned to me";
  private static final String TICKET_CREATED = "Ticket created";

  private final TicketRepository ticketRepository;
  private final UserServiceHelper userServiceHelper;
  private final ApplicationEventPublisher eventPublisher;

  private UserMappingContext createContext() {
    return new UserMappingContext(userServiceHelper);
  }

  @Override
  public Page<TicketResponseDto> findAll(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAllActiveTickets(pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public Page<TicketResponseDto> findArchivedTickets(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAllArchivedTickets(pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public Page<TicketResponseDto> findAllCreatedByCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findActiveTicketsByReporterId(currentUserId, pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public Page<TicketResponseDto> findArchivedTicketsCreatedByCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findArchivedTicketsByReporterId(currentUserId, pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public Page<TicketResponseDto> findAllAssignedOnCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findActiveTicketsByAssigneeId(currentUserId, pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public Page<TicketResponseDto> findArchivedTicketsAssignedOnCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findArchivedTicketsByAssigneeId(currentUserId, pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket -> TICKET_MAPPER.toDto(ticket, context));
  }

  @Override
  public TicketResponseDto findById(String id) {
    Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    return TICKET_MAPPER.toDto(ticket, createContext());
  }

  @Override
  public void create(TicketRequestDto requestDto) {
    Ticket ticket = TICKET_MAPPER.toEntity(requestDto).init();
    String userId = UserUtil.getCurrentUserId();
    ticket.setReporterId(userId);

    userServiceHelper.userExistsByIdIfNotNull(requestDto.getAssigneeId());

    Ticket savedTicket = ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, savedTicket.getId(), TICKET_CREATED)
    );
  }

  @Override
  public void assignOnMe(String ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    ticket.setAssigneeId(UserUtil.getCurrentUserId());

    ticketRepository.save(ticket);

    eventPublisher.publishEvent(
        new TicketEvent(this, ticketId, TICKET_ASSIGNED_TO_ME)
    );
  }
}