package com.gitlab.microservice.service.auth;

import com.gitlab.microservice.dto.UserCreateRequestDto;

public interface AuthService {

  void register(UserCreateRequestDto requestDto);
}
