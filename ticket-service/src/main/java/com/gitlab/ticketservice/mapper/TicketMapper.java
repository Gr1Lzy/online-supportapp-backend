package com.gitlab.ticketservice.mapper;

import com.gitlab.ticketservice.dto.ticket.TicketRequestDto;
import com.gitlab.ticketservice.dto.ticket.TicketResponseDto;
import com.gitlab.ticketservice.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  TicketMapper TICKET_MAPPER = Mappers.getMapper(TicketMapper.class);

  TicketResponseDto toDto(Ticket ticket);

  Ticket toEntity(TicketRequestDto requestDto);
}
