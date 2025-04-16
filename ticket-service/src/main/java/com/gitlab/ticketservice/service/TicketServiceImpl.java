package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.mapper.context.UserMappingContext;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.UserServiceHelper;
import com.gitlab.ticketservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private static final Sort DEFAULT_SORT = Sort.by("id").ascending();

  private final TicketRepository ticketRepository;
  private final UserServiceHelper userServiceHelper;

  private UserMappingContext createContext() {
    return new UserMappingContext(userServiceHelper);
  }

  @Override
  public Page<TicketResponseDto> findAll(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAll(pageable);

    UserMappingContext context = createContext();

    return tickets.map(ticket ->
        TICKET_MAPPER.toDto(ticket, context)
    );
  }

  @Override
  public Page<TicketResponseDto> findAllCreatedByCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAllByReporterId(currentUserId, pageable);

    UserMappingContext context = createContext();
    return tickets.map(ticket ->
        TICKET_MAPPER.toDto(ticket, context)
    );
  }

  @Override
  public Page<TicketResponseDto> findAllAssignedOnCurrentUser(Integer page, Integer size) {
    String currentUserId = UserUtil.getCurrentUserId();

    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAllByAssigneeId(currentUserId, pageable);

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

    ticketRepository.save(ticket);
  }

  @Override
  public void assignOnMe(String ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    ticket.setAssigneeId(UserUtil.getCurrentUserId());

    ticketRepository.save(ticket);
  }
}
