package com.gitlab.ticketservice.mapper.context;

import com.gitlab.ticketservice.util.UserServiceHelper;
import lombok.Getter;


@Getter
public class UserMappingContext {
  private final UserServiceHelper userService;

  public UserMappingContext(UserServiceHelper userService) {
    this.userService = userService;
  }
}
