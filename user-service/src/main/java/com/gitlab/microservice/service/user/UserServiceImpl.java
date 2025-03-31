package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.page.PageDto;
import com.gitlab.microservice.dto.password.PasswordRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gitlab.microservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override
  public UserResponseDto getCurrentUser() {

    UserResource usersResource = getResource().get(UserUtil.getCurrentUserId());

    return USER_MAPPER.toDto(usersResource.toRepresentation());
  }

  @Override
  public UserResponseDto findById(String id) {
    UserResource usersResource = getResource().get(id);

    return USER_MAPPER.toDto(usersResource.toRepresentation());
  }

  @Override
  public PageDto<UserResponseDto> findAll(Integer page, Integer size) {
    UsersResource userResource = getResource();

    Integer first = page * size;

    List<UserRepresentation> users = userResource.list(first, size + 1);

    Boolean hasNext = users.size() > size;

    List<UserResponseDto> content = users.stream()
        .limit(size)
        .map(USER_MAPPER::toDto)
        .toList();

    return new PageDto<>(content, page, size, hasNext);
  }

  @Override
  public void updatePassword(PasswordRequestDto requestDto) {
    String userId = UserUtil.getCurrentUserId();

    CredentialRepresentation newCredential = new CredentialRepresentation();
    newCredential.setType(CredentialRepresentation.PASSWORD);
    newCredential.setValue(requestDto.getNewPassword());
    newCredential.setTemporary(false);

    UserResource userResource = getResource().get(userId);
    userResource.resetPassword(newCredential);
  }

  private UsersResource getResource() {
    return keycloak.realm(realm).users();
  }
}
