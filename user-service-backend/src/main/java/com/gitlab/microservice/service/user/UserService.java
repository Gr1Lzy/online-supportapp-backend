package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.user.UserRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;

public interface UserService {

  void create(UserRequestDto requestDto);

  UserResponseDto findById(String id);
}
