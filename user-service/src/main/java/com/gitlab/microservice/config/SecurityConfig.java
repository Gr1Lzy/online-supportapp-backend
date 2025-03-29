package com.gitlab.microservice.config;

import com.gitlab.microservice.exception.custom.CustomAccessDeniedHandler;
import com.gitlab.microservice.exception.custom.CustomAuthenticationFailureHandler;
import com.gitlab.microservice.util.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthConverter jwtAuthConverter;
  private final CustomAuthenticationFailureHandler authenticationFailureHandler;
  private final CustomAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests(request -> request
            .requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html").permitAll()
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        )

        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(authenticationFailureHandler::onAuthenticationFailure)
            .accessDeniedHandler(accessDeniedHandler)
        )


        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
        )

        .sessionManagement(session -> session
            .sessionCreationPolicy(STATELESS));

    return httpSecurity.build();
  }
}

