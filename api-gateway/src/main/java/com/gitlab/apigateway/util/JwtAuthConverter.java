package com.gitlab.apigateway.util;

import io.micrometer.common.lang.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

  private final ReactiveJwtAuthenticationConverter jwtAuthenticationConverter =
      new ReactiveJwtAuthenticationConverter();

  public JwtAuthConverter() {
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
  }

  @Override
  public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
    return jwtAuthenticationConverter.convert(jwt);
  }

  private Flux<GrantedAuthority> extractAuthorities(Jwt jwt) {
    Map<String, Object> claims = jwt.getClaims();
    Collection<GrantedAuthority> authorities = extractRealmRoles(claims);

    return Flux.fromIterable(authorities);
  }

  private Collection<GrantedAuthority> extractRealmRoles(Map<String, Object> claims) {
    if (claims.get("realm_access") instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");

      if (realmAccess.get("roles") instanceof List) {
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
      }
    }

    return Collections.emptyList();
  }
}