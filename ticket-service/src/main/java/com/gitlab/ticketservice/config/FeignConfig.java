package com.gitlab.ticketservice.config;

import com.gitlab.ticketservice.exception.AuthenticationException;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "com.gitlab.ticketservice.client")
public class FeignConfig {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication instanceof JwtAuthenticationToken jwtToken) {
        requestTemplate.header("Authorization", "Bearer " + jwtToken.getToken().getTokenValue());
      } else {
        throw new AuthenticationException("Invalid Token");
      }
    };
  }
}
