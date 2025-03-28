package com.gitlab.microservice.util;

import com.gitlab.microservice.dto.UserCreateRequestDto;
import lombok.experimental.UtilityClass;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;

@UtilityClass
public class UserRepresentationUtil {

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
}
