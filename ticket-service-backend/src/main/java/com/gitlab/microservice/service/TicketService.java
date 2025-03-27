package com.gitlab.microservice.service;

import com.gitlab.microservice.dto.ticket.TicketRequestDto;
import com.gitlab.microservice.dto.ticket.TicketResponseDto;
import org.springframework.data.domain.Page;

public interface TicketService {

  Page<TicketResponseDto> findAll(Integer page, Integer size);

  TicketResponseDto findById(String id);

  void create(TicketRequestDto requestDto);
}
