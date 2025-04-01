package com.gitlab.userservice.service.user;

import com.gitlab.userservice.dto.page.PageDto;
import com.gitlab.userservice.dto.role.RoleRequestDto;
import com.gitlab.userservice.dto.user.AdminUserResponseDto;
import com.gitlab.userservice.dto.user.UserUpdateRequestDto;
import com.gitlab.userservice.exception.standard.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.gitlab.userservice.mapper.UserMapper.USER_MAPPER;

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
    UserRepresentation user;

    try {
      user = userResource.get(id).toRepresentation();
    } catch (Exception e) {
      throw new EntityNotFoundException("User not found");
    }

    USER_MAPPER.updateEntity(requestDto, user);
    userResource.get(user.getId()).update(user);
  }

  @Override
  public void delete(String id) {
    UsersResource userResource = getResource();
    userResource.get(id).remove();
  }

  @Override
  public void assignRole(String id, RoleRequestDto requestDto) {
    UsersResource userResource = getResource();
    UserRepresentation user = userResource.get(id).toRepresentation();

    RoleRepresentation role = keycloak.realm(realm)
        .roles()
        .get(requestDto.getRole())
        .toRepresentation();

    userResource.get(user.getId())
        .roles()
        .realmLevel()
        .add(Collections.singletonList(role));
  }

  @Override
  public AdminUserResponseDto findById(String id) {
    UsersResource userResource = getResource();
    UserRepresentation user;

    try {
      user = userResource.get(id).toRepresentation();
    } catch (Exception e) {
      throw new EntityNotFoundException("User not found");
    }

    return USER_MAPPER.toAdminDto(user, extractRealmRoles(user));
  }


  @Override
  public PageDto<AdminUserResponseDto> findAll(Integer page, Integer size) {
    UsersResource userResource = getResource();

    Integer first = page * size;

    List<UserRepresentation> users = userResource.list(first, size + 1);

    Boolean hasNext = users.size() > size;

    List<AdminUserResponseDto> content = users.stream()
        .limit(size)
        .map(user ->
            USER_MAPPER.toAdminDto(user, extractRealmRoles(user)))
        .toList();

    return new PageDto<>(content, page, size, hasNext);
  }

  private UsersResource getResource() {
    return keycloak.realm(realm).users();
  }

  private List<String> extractRealmRoles(UserRepresentation user) {
    List<RoleRepresentation> roles = getResource()
        .get(user.getId())
        .roles()
        .realmLevel()
        .listAll();

    return roles.stream()
        .map(RoleRepresentation::getName)
        .filter(role -> role.startsWith("ROLE_"))
        .toList();
  }
}
