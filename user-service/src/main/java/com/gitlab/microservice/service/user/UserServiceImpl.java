package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.gitlab.microservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override
  public UserResponseDto getCurrentUser() {

    UserResource usersResource = keycloak.realm(realm).users().get(UserUtil.getCurrentUserId());

    return USER_MAPPER.toDto(usersResource.toRepresentation());
  }
}
