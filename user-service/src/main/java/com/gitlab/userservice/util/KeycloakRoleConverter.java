package com.gitlab.userservice.util;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
    List<String> roles = source.getClaimAsStringList("roles");
    return roles.stream()
        .map(role -> (GrantedAuthority) () -> role)
        .toList();
  }
}