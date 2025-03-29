package com.gitlab.microservice.service.auth;

import com.gitlab.microservice.dto.auth.AuthRequestDto;
import com.gitlab.microservice.dto.auth.AuthResponseDto;
import com.gitlab.microservice.dto.user.UserCreateRequestDto;

public interface AuthService {

  void register(UserCreateRequestDto requestDto);

  AuthResponseDto login(AuthRequestDto authRequestDto);
}
