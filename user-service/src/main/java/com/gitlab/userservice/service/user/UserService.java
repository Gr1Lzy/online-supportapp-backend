package com.gitlab.userservice.service.user;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.password.PasswordRequestDto;
import com.gitlab.userservice.dto.user.UserResponseDto;

public interface UserService {

  UserResponseDto getCurrentUser();

  UserResponseDto findById(String id);

  PageDto<UserResponseDto> findAll(Integer page, Integer size);

  void updatePassword(PasswordRequestDto requestDto);
}
