package com.gitlab.microservice.service.user.impl;

import com.gitlab.microservice.dto.admin.AdminUserCreateRequestDto;
import com.gitlab.microservice.dto.admin.AdminUserResponseDto;
import com.gitlab.microservice.dto.admin.AdminUserUpdateRequestDto;
import com.gitlab.microservice.entity.User;
import com.gitlab.microservice.exception.EntityExistException;
import com.gitlab.microservice.exception.EntityNotFoundException;
import com.gitlab.microservice.repository.UserRepository;
import com.gitlab.microservice.service.user.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.gitlab.microservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

  private static final Sort DEFAULT_SORT = Sort.by("id").ascending();

  private final UserRepository userRepository;

  @Override
  public Page<AdminUserResponseDto> findAll(Integer page, Integer size) {
    Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
    return userRepository.findAll(pageable)
        .map(USER_MAPPER::toAdminDto);
  }

  @Override
  public AdminUserResponseDto findById(String id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return USER_MAPPER.toAdminDto(user);
  }

  @Override
  public void create(AdminUserCreateRequestDto requestDto) {
    User user = USER_MAPPER.toEntity(requestDto);

    if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
      throw new EntityExistException("User already exists");
    }

    userRepository.save(user);
  }

  @Override
  public void update(String id, AdminUserUpdateRequestDto requestDto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    USER_MAPPER.updateUserFromDto(requestDto, user);

    userRepository.save(user);
  }

  @Override
  public void delete(String id) {
    userRepository.deleteById(id);
  }
}
