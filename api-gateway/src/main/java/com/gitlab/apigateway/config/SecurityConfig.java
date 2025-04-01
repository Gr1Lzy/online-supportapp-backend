package com.gitlab.apigateway.config;

import com.gitlab.apigateway.util.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthConverter jwtAuthConverter;

  private static final String[] PUBLIC_PATHS = {
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/user-service/**",
      "/ticket-service/**",
      "/api/auth/**",
  };

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(ServerHttpSecurity.CorsSpec::disable)

        .authorizeExchange(exchanges -> exchanges
            .pathMatchers(PUBLIC_PATHS).permitAll()
            .pathMatchers("/api/admin/**").hasRole("ADMIN")
            .pathMatchers("/api/tickets/**").hasRole("USER")
            .pathMatchers("/api/users/**").hasRole("USER")
            .anyExchange().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
        )
        .build();
  }
}