package com.gitlab.microservice.service.user;

import com.gitlab.microservice.dto.RoleRequestDto;
import com.gitlab.microservice.dto.UserResponseDto;
import com.gitlab.microservice.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gitlab.microservice.mapper.UserMapper.USER_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override

  public void update(String id, UserUpdateRequestDto requestDto) {
    UsersResource userResource = getResource();
    UserRepresentation user = userResource.get(id).toRepresentation();

    UserRepresentation updated = USER_MAPPER.updateEntity(requestDto, user);
    userResource.get(id).update(updated);
  }

  @Override
  public void delete(String id) {
    UsersResource userResource = getResource();
    userResource.get(id).remove();
  }

  @Override
  public void assignRole(String id, RoleRequestDto requestDto) {

  }

  @Override
  public UserResponseDto findById(String id) {
    return null;
  }


  @Override
  public List<UserResponseDto> findAll(Integer page, Integer size) {
    UsersResource userResource = getResource();
    int first = page * size;

    return userResource.list(first, size).stream()
        .map(USER_MAPPER::toDto)
        .toList();
  }

  private UsersResource getResource() {
    return keycloak.realm(realm).users();
  }
}
