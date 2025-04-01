package com.gitlab.ticketservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String SCHEME_NAME = "Authentication Bearer";
  private static final String SCHEME = "bearer";
  private static final String BEARER_FORMAT = "JWT";

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement()
            .addList(SCHEME_NAME))
        .components(new Components()
            .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)


                .scheme(SCHEME)
                .bearerFormat(BEARER_FORMAT)
                .in(SecurityScheme.In.HEADER)));
  }
}

