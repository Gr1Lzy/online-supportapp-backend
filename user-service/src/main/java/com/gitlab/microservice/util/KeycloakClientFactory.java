package com.gitlab.microservice.util;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakClientFactory {

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.serverUrl}")
  private String serverUrl;

  @Value("${keycloak.admin.id}")
  private String adminClientId;

  @Value("${keycloak.admin.secret}")
  private String adminClientSecret;

  @Value("${keycloak.client.id}")
  private String publicClientId;

  @Value("${keycloak.client.secret}")
  private String publicClientSecret;

  public Keycloak createAdminClient() {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(adminClientId)
        .clientSecret(adminClientSecret)
        .grantType("client_credentials")
        .build();
  }

  public Keycloak createUserClient(String username, String password) {
    return KeycloakBuilder.builder()
        .serverUrl(serverUrl)
        .realm(realm)
        .clientId(publicClientId)
        .clientSecret(publicClientSecret)
        .username(username)
        .password(password)
        .grantType("password")
        .build();
  }
}