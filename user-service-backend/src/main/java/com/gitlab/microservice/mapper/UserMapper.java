package com.gitlab.microservice.mapper;

import com.gitlab.microservice.dto.admin.AdminUserCreateRequestDto;
import com.gitlab.microservice.dto.admin.AdminUserResponseDto;
import com.gitlab.microservice.dto.admin.AdminUserUpdateRequestDto;
import com.gitlab.microservice.dto.user.UserRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

  User toEntity(UserRequestDto requestDto);

  UserResponseDto toDto(User user);

  User toEntity(AdminUserCreateRequestDto requestDto);

  AdminUserResponseDto toAdminDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromDto(AdminUserUpdateRequestDto dto, @MappingTarget User user);
}
