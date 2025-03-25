package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.user.UserRequestDto;

public interface UserService {

  void create(UserRequestDto requestDto);
}
