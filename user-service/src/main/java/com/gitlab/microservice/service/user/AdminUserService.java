package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.role.RoleRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.dto.user.UserUpdateRequestDto;

import java.util.List;

public interface AdminUserService {

  void update(String id, UserUpdateRequestDto requestDto);

  void delete(String id);

  void assignRole(String id, RoleRequestDto requestDto);

  UserResponseDto findById(String id);

  List<UserResponseDto> findAll(Integer page, Integer size);
}
