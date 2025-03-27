package com.gitlab.microservice.mapper;

import com.gitlab.microservice.dto.ticket.TicketRequestDto;
import com.gitlab.microservice.dto.ticket.TicketResponseDto;
import com.gitlab.microservice.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

  TicketMapper TICKET_MAPPER = Mappers.getMapper(TicketMapper.class);

  TicketResponseDto toDto(Ticket ticket);

  Ticket toEntity(TicketRequestDto requestDto);
}
