package com.gitlab.ticketservice.config;

import com.gitlab.ticketservice.exception.custom.CustomAccessDeniedHandler;
import com.gitlab.ticketservice.exception.custom.CustomJwtAuthEntryPoint;
import com.gitlab.ticketservice.util.KeycloakRoleConverter;
import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomJwtAuthEntryPoint jwtAuthEntryPoint;

  @Bean

  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests(request -> request
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .requestMatchers("/api/tickets/**").hasRole("USER")
            .requestMatchers("/api/support/tickets/**").hasRole("SUPPORT")
        )

        .exceptionHandling(ex -> ex
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
        )

        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        )

        .sessionManagement(session -> session
            .sessionCreationPolicy(STATELESS));

    return httpSecurity.build();
  }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    converter.setPrincipalClaimName("id");
    return converter;
  }
}

