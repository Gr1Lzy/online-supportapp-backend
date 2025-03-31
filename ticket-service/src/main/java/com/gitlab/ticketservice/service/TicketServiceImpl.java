package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.entity.Ticket;
import com.gitlab.ticketservice.exception.EntityNotFoundException;
import com.gitlab.ticketservice.repository.TicketRepository;
import com.gitlab.ticketservice.util.UserServiceHelper;
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

  @Override
  public Page<TicketResponseDto> findAll(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    Page<Ticket> tickets = ticketRepository.findAll(pageable);

    return tickets.map(ticket -> {
      TicketResponseDto responseDto = TICKET_MAPPER.toDto(ticket);
      responseDto.setAssignee(userServiceHelper.safeFindById(ticket.getAssigneeId()));
      responseDto.setReporter(userServiceHelper.safeFindById(ticket.getReporterId()));
      return responseDto;
    });
  }

  @Override
  public TicketResponseDto findById(String id) {
    Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

    TicketResponseDto responseDto = TICKET_MAPPER.toDto(ticket);
    responseDto.setAssignee(userServiceHelper.safeFindById(ticket.getAssigneeId()));
    responseDto.setReporter(userServiceHelper.safeFindById(ticket.getReporterId()));
    return responseDto;
  }

  @Override
  public void create(TicketRequestDto requestDto) {
    Ticket ticket = TICKET_MAPPER.toEntity(requestDto).init();

    userServiceHelper.userExistsByIdIfNotNull(requestDto.getReporterId());
    userServiceHelper.userExistsByIdIfNotNull(requestDto.getAssigneeId());

    ticketRepository.save(ticket);
  }
}
