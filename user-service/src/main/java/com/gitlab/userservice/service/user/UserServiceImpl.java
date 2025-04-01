package com.gitlab.userservice.service.user;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.password.PasswordRequestDto;
import com.gitlab.userservice.dto.user.UserResponseDto;
import com.gitlab.userservice.exception.standard.EntityNotFoundException;
import com.gitlab.userservice.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gitlab.userservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override
  public UserResponseDto getCurrentUser() {
    UsersResource userResource = getResource();
    UserRepresentation user = userResource.get(UserUtil.getCurrentUserId()).toRepresentation();

    return USER_MAPPER.toDto(user);
  }

  @Override
  public UserResponseDto findById(String id) {
    UsersResource userResource = getResource();
    UserRepresentation user;

    try {
      user = userResource.get(id).toRepresentation();
    } catch (Exception e) {
      throw new EntityNotFoundException("User not found");
    }

    return USER_MAPPER.toDto(user);
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
