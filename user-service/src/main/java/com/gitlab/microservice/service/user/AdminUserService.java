package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.RoleRequestDto;
import com.gitlab.microservice.dto.UserResponseDto;
import com.gitlab.microservice.dto.UserUpdateRequestDto;

import java.util.List;

public interface AdminUserService {

  void update(String id, UserUpdateRequestDto requestDto);

  void delete(String id);

  void assignRole(String id, RoleRequestDto requestDto);

  UserResponseDto findById(String id);

  List<UserResponseDto> findAll(Integer page, Integer size);
}
