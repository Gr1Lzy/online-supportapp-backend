package com.gitlab.microservice.mapper;

import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.dto.user.UserUpdateRequestDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

  UserResponseDto toDto(UserRepresentation user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  UserRepresentation updateEntity(UserUpdateRequestDto dto, @MappingTarget UserRepresentation user);
}
