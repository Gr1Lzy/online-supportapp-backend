package com.gitlab.userservice.mapper;

import com.gitlab.userservice.dto.user.AdminUserResponseDto;
import com.gitlab.userservice.dto.user.UserResponseDto;
import com.gitlab.userservice.dto.user.UserUpdateRequestDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

  UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

  UserResponseDto toDto(UserRepresentation user);

  AdminUserResponseDto toAdminDto(UserRepresentation user, List<String> roles);

  @BeanMapping(
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      ignoreByDefault = true)
  void updateEntity(UserUpdateRequestDto dto, @MappingTarget UserRepresentation user);
}
