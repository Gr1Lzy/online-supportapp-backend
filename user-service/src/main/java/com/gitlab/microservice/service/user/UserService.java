package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.page.PageDto;
import com.gitlab.microservice.dto.password.PasswordRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;

public interface UserService {

  UserResponseDto getCurrentUser();

  UserResponseDto findById(String id);

  PageDto<UserResponseDto> findAll(Integer page, Integer size);

  void updatePassword(PasswordRequestDto requestDto);
}
