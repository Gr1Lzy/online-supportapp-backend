package com.gitlab.ticketservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserUtil {

  public static String getCurrentUserId() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
