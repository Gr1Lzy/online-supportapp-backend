package com.gitlab.microservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@EnableFeignClients(basePackages = "com.gitlab.microservice.client")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class WebConfig {
}
