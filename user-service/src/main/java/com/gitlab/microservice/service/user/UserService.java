package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.user.UserResponseDto;

public interface UserService {

  UserResponseDto getCurrentUser();

}
