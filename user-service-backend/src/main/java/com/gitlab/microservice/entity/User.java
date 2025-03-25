package com.gitlab.microservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "users")
public class User extends AbstractEntity {

  @Field("email")
  private String email;

  @Field("password")
  private String password;

  @Field("first_name")
  private String firstName;

  @Field("last_name")
  private String lastName;

  @Field("role")
  private UserRole role;

  public User init() {
    this.role = UserRole.ROLE_USER;
    return this;
  }
}
