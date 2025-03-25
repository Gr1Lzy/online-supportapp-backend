package com.gitlab.microservice.repository;

import com.gitlab.microservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

  boolean existsByEmailIgnoreCase(String email);
}
