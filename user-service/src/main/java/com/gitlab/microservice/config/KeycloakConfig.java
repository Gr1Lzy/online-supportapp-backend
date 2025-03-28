package com.gitlab.microservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

  @Value("${keycloak.admin.clientId}")
  private String clientId;

  @Value("${keycloak.admin.clientSecret}")
  private String clientSecret;

  @Value("${keycloak.realm}")
  private String realm;

  @Value("${keycloak.serverUrl}")
  private String serverUrl;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType("client_credentials")
        .realm(realm)
        .serverUrl(serverUrl)
        .build();
  }
}
