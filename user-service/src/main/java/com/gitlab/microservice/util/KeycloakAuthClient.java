package com.gitlab.microservice.util;

import com.gitlab.microservice.exception.standard.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakAuthClient {

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

  private final RestTemplate restTemplate;

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

  public AccessTokenResponse getRefreshToken(String refreshToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("grant_type", "refresh_token");
    formData.add("client_id", publicClientId);
    formData.add("client_secret", publicClientSecret);
    formData.add("refresh_token", refreshToken);

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

    String refreshUrl = String.format("%s/realms/%s/protocol/openid-connect/token", serverUrl, realm);

    ResponseEntity<AccessTokenResponse> response = restTemplate
        .postForEntity(refreshUrl, requestEntity,
            AccessTokenResponse.class);

    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
      return response.getBody();
    } else {
      throw new AuthenticationException("Failed to refresh token");
    }
  }
}
