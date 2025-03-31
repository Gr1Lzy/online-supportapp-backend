package com.gitlab.ticketservice.client;

import com.gitlab.ticketservice.config.FeignConfig;
import com.gitlab.ticketservice.dto.user.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",
    url = "${user.service.url}/api/users",
    configuration = FeignConfig.class)
public interface UserClient {

  @GetMapping("/{id}")
  UserResponseDto findById(@PathVariable("id") String id);
}