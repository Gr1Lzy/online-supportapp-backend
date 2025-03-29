package com.gitlab.microservice.config;

import com.gitlab.microservice.util.KeycloakClientFactory;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

  private final KeycloakClientFactory keycloakClientFactory;

  @Bean
  public Keycloak keycloak() {
    return keycloakClientFactory.createAdminClient();
  }
}
