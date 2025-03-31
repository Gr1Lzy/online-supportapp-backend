package com.gitlab.microservice.service.auth;

import com.gitlab.microservice.dto.auth.AuthRequestDto;
import com.gitlab.microservice.dto.auth.AuthResponseDto;
import com.gitlab.microservice.dto.auth.RefreshRequestDto;
import com.gitlab.microservice.dto.user.UserCreateRequestDto;
import com.gitlab.microservice.exception.standard.AuthenticationException;
import com.gitlab.microservice.exception.standard.EntityExistException;
import com.gitlab.microservice.exception.standard.InvalidTokenException;
import com.gitlab.microservice.util.KeycloakAuthClient;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.gitlab.microservice.entity.UserRole.ROLE_USER;
import static com.gitlab.microservice.util.UserUtil.extractUserId;
import static com.gitlab.microservice.util.UserUtil.getUserRepresentation;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  private final KeycloakAuthClient keycloakAuthClient;

  @Override
  public void register(UserCreateRequestDto requestDto) {
    UserRepresentation user = getUserRepresentation(requestDto);

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


  @Override
  public AuthResponseDto login(AuthRequestDto authRequestDto) {
    try (Keycloak userKeycloak = keycloakAuthClient.createUserClient(
        authRequestDto.getUsername(),
        authRequestDto.getPassword())) {

      AccessTokenResponse tokenResponse = userKeycloak.tokenManager().getAccessToken();

      return AuthResponseDto.builder()
          .tokenType(tokenResponse.getTokenType())
          .accessToken(tokenResponse.getToken())
          .refreshToken(tokenResponse.getRefreshToken())
          .expiresIn(tokenResponse.getExpiresIn())
          .build();

    } catch (Exception e) {
      log.error("Authentication failed for user {}: {}", authRequestDto.getUsername(), e.getMessage());
      throw new AuthenticationException("Invalid credentials");
    }
  }

  @Override
  public AuthResponseDto refresh(RefreshRequestDto requestDto) {
    try {
      AccessTokenResponse tokenResponse = keycloakAuthClient.getRefreshToken(requestDto.getRefreshToken());

      return AuthResponseDto.builder()
          .tokenType(tokenResponse.getTokenType())
          .accessToken(tokenResponse.getToken())
          .refreshToken(tokenResponse.getRefreshToken())
          .expiresIn(tokenResponse.getExpiresIn())
          .build();
    } catch (Exception e) {
      throw new InvalidTokenException("Invalid refresh token");
    }
  }
}
