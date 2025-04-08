package com.gitlab.ticketservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogMapper {
  LogMapper LOG_MAPPER = Mappers.getMapper(LogMapper.class);
}
