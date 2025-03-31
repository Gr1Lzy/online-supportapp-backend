package com.gitlab.userservice.service.auth;

import com.gitlab.userservice.dto.auth.AuthRequestDto;
import com.gitlab.userservice.dto.auth.AuthResponseDto;
import com.gitlab.userservice.dto.auth.RefreshRequestDto;
import com.gitlab.userservice.dto.user.UserCreateRequestDto;

public interface AuthService {

  void register(UserCreateRequestDto requestDto);

  AuthResponseDto login(AuthRequestDto requestDto);

  AuthResponseDto refresh(RefreshRequestDto requestDto);
}
