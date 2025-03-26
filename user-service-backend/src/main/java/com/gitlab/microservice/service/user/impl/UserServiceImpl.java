package com.gitlab.microservice.service.user.impl;

import com.gitlab.microservice.dto.user.UserRequestDto;
import com.gitlab.microservice.dto.user.UserResponseDto;
import com.gitlab.microservice.entity.User;
import com.gitlab.microservice.exception.EntityExistException;
import com.gitlab.microservice.exception.EntityNotFoundException;
import com.gitlab.microservice.repository.UserRepository;
import com.gitlab.microservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gitlab.microservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public void create(UserRequestDto requestDto) {
    User user = USER_MAPPER.toEntity(requestDto).init();

    if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
      throw new EntityExistException("User already exists");
    }

    userRepository.save(user);
  }

  @Override
  public UserResponseDto findById(String id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return USER_MAPPER.toDto(user);
  }
}
