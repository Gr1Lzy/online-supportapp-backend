package com.gitlab.microservice.client;

import com.gitlab.microservice.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}/api/user")
public interface UserClient {

  @GetMapping("/{id}")
  UserResponseDto findById(@PathVariable("id") String id);
}
