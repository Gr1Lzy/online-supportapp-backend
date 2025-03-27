package com.gitlab.microservice.util;

import com.gitlab.microservice.client.UserClient;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.excetpion.EntityNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceHelper {
  private final UserClient userClient;

  public void userExistsByIdIfNotNull(String id) {
    if (id == null) {
      return;
    }

    try {
      userClient.findById(id);
    } catch (FeignException.NotFound e) {
      throw new EntityNotFoundException("User with ID " + id + " not found");
    } catch (FeignException e) {
      log.error("Error calling user service: {}", e.getMessage());
      throw new IllegalArgumentException("User service is currently unavailable");
    }
  }

  public UserResponseDto safeFindById(String id) {
    if (id == null) {
      return null;
    }

    try {
      return userClient.findById(id);
    } catch (FeignException.NotFound e) {
      log.warn("User not found with ID: {}", id);
      return null;
    } catch (FeignException e) {
      log.error("Error retrieving user: {}", e.getMessage());
      return null;
    }
  }
}