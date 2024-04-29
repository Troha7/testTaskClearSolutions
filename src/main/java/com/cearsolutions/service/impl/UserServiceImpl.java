package com.cearsolutions.service.impl;

import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.EmailNotUniqException;
import com.cearsolutions.mapper.UserMapper;
import com.cearsolutions.model.User;
import com.cearsolutions.repository.UserRepository;
import com.cearsolutions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link UserServiceImpl}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  @Override
  public UserResponseDto create(UserRequestDto userRequestDto) {
    var email = userRequestDto.getEmail();
    log.trace("Started create new user [{}]", email);

    checkingUniqueEmail(email);

    var user = userMapper.toEntity(userRequestDto);
    User savedUser = userRepository.save(user);

    log.info("New user [{}] was created", email);
    return userMapper.toResponseDto(savedUser);
  }

  private void checkingUniqueEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      log.warn("Email [{}] already in use", email);
      throw new EmailNotUniqException(String.format("Email %s already in use", email));
    }
  }
}
