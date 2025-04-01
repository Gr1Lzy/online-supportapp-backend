package com.gitlab.ticketservice.service;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import org.springframework.data.domain.Page;

public interface TicketService {

  Page<TicketResponseDto> findAll(Integer page, Integer size);

  TicketResponseDto findById(String id);

  void create(TicketRequestDto requestDto);
}
