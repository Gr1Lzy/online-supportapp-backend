package com.gitlab.microservice.config;

import com.gitlab.microservice.util.KeycloakAuthClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final KeycloakAuthClient keycloakAuthClient;

  @Bean
  public Keycloak keycloak() {
    return keycloakAuthClient.createAdminClient();
  }
}
