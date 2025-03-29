package com.gitlab.microservice.util;

import com.gitlab.microservice.dto.user.UserCreateRequestDto;
import com.gitlab.microservice.exception.EntityExistException;
import jakarta.ws.rs.core.Response;
import lombok.experimental.UtilityClass;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@UtilityClass
public class UserUtil {

  public static UserRepresentation getUserRepresentation(UserCreateRequestDto requestDto) {
    CredentialRepresentation passwordCred = new CredentialRepresentation();
    passwordCred.setTemporary(false);
    passwordCred.setType(CredentialRepresentation.PASSWORD);
    passwordCred.setValue(requestDto.getPassword());

    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setUsername(requestDto.getUsername());
    user.setFirstName(requestDto.getFirstName());
    user.setLastName(requestDto.getLastName());
    user.setEmail(requestDto.getEmail());
    user.setEmailVerified(true);
    user.setCredentials(Collections.singletonList(passwordCred));
    return user;
  }

  public static String extractUserId(Response response) {
    String location = response.getHeaderString("Location");
    if (location == null) {
      throw new EntityExistException("Failed to create Keycloak user");
    }
    return location.substring(location.lastIndexOf("/") + 1);
  }

  public static String getCurrentUserId() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
