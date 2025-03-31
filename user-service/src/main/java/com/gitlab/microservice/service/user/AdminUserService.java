package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.page.PageDto;
import com.gitlab.microservice.dto.role.RoleRequestDto;
import com.gitlab.microservice.dto.user.AdminUserResponseDto;
import com.gitlab.microservice.dto.user.UserUpdateRequestDto;

public interface AdminUserService {

  void update(String id, UserUpdateRequestDto requestDto);

  void delete(String id);

  void assignRole(String id, RoleRequestDto requestDto);

  AdminUserResponseDto findById(String id);

  PageDto<AdminUserResponseDto> findAll(Integer page, Integer size);
}
