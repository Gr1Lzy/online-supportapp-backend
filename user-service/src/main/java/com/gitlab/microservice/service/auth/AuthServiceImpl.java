package com.gitlab.microservice.service.auth;

import com.gitlab.microservice.dto.UserCreateRequestDto;
import com.gitlab.microservice.exception.EntityExistException;
import com.gitlab.microservice.util.UserRepresentationUtil;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.gitlab.microservice.entity.UserRole.ROLE_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override
  public void register(UserCreateRequestDto requestDto) {
    UserRepresentation user = UserRepresentationUtil.getUserRepresentation(requestDto);

    UsersResource usersResource = keycloak.realm(realm).users();

    try (Response response = usersResource.create(user)) {
      if (response.getStatus() == 409) {
        String errorMessage = response.readEntity(String.class);
        log.warn("Keycloak user creation failed: {}", errorMessage);
        throw new EntityExistException(errorMessage);
      }

      String userId = extractUserId(response);

      RoleRepresentation role = keycloak.realm(realm)
          .roles()
          .get(ROLE_USER.name())
          .toRepresentation();

      usersResource.get(userId)
          .roles()
          .realmLevel()
          .add(Collections.singletonList(role));
    }
  }

  private String extractUserId(Response response) {
    String location = response.getHeaderString("Location");
    if (location == null) {
      throw new EntityExistException("Failed to create Keycloak user");
    }
    return location.substring(location.lastIndexOf("/") + 1);
  }
}
