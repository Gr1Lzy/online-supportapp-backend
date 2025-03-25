package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.admin.AdminUserCreateRequestDto;
import com.gitlab.microservice.dto.admin.AdminUserResponseDto;
import com.gitlab.microservice.dto.admin.AdminUserUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface AdminUserService {

  Page<AdminUserResponseDto> findAll(Integer page, Integer size);

  AdminUserResponseDto findById(String id);

  void create(AdminUserCreateRequestDto requestDto);

  void update(String id, AdminUserUpdateRequestDto requestDto);

  void delete(String id);
}
