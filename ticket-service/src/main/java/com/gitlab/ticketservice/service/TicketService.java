package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import org.springframework.data.domain.Page;

public interface TicketService {

  Page<TicketResponseDto> findAll(Integer page, Integer size);

  Page<TicketResponseDto> findAllCreatedByCurrentUser(Integer page, Integer size);

  Page<TicketResponseDto> findAllAssignedOnCurrentUser(Integer page, Integer size);

  TicketResponseDto findById(String ticketId);

  void create(TicketRequestDto requestDto);

  void assignOnMe(String ticketId);
}
