package com.gitlab.apigateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

  private static final String SCHEME_NAME = "bearerAuth";
  private static final String SCHEME = "bearer";
  private static final String BEARER_FORMAT = "JWT";

  @Bean
  public OpenAPI apiGatewayOpenApi(@Value("${spring.application.name}") String appName) {
    return new OpenAPI()
        .info(new Info()
            .title(appName.toUpperCase() + " API")
            .description("API Gateway for Online Support Application")
            .version("1.0.0"))
        .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
        .components(new Components()
            .addSecuritySchemes(SCHEME_NAME,
                new SecurityScheme()
                    .name(SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(SCHEME)
                    .bearerFormat(BEARER_FORMAT)
                    .in(SecurityScheme.In.HEADER)
            ))
        .servers(List.of(
            new Server().url("/").description("API Gateway")
        ));
  }
}