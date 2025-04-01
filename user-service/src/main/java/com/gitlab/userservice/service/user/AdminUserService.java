package com.gitlab.userservice.service.user;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.role.RoleRequestDto;
import com.gitlab.userservice.dto.user.AdminUserResponseDto;
import com.gitlab.userservice.dto.user.UserUpdateRequestDto;

public interface AdminUserService {

  void update(String id, UserUpdateRequestDto requestDto);

  void delete(String id);

  void assignRole(String id, RoleRequestDto requestDto);

  AdminUserResponseDto findById(String id);

  PageDto<AdminUserResponseDto> findAll(Integer page, Integer size);
}
