package com.cearsolutions.service.impl;

import com.cearsolutions.dto.request.UpdateUserRequestDto;
import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.DateSearchException;
import com.cearsolutions.exception.EmailNotUniqException;
import com.cearsolutions.exception.UserNotFoundException;
import com.cearsolutions.mapper.UserMapper;
import com.cearsolutions.model.User;
import com.cearsolutions.repository.UserRepository;
import com.cearsolutions.service.UserService;
import java.time.LocalDate;
import java.util.List;
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

  @Transactional
  @Override
  public UserResponseDto updateById(Long id, UpdateUserRequestDto userRequestDto) {
    log.trace("Started update user id [{}]", id);

    var user = findById(id);

    userMapper.updateEntity(user, userRequestDto);

    User updatedUser = userRepository.save(user);

    log.info("Update user id [{}] was seccesfuly", id);
    return userMapper.toResponseDto(updatedUser);
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    log.trace("Started delete user by id [{}]", id);

    findById(id);

    userRepository.deleteById(id);

    log.info("Delete user by id [{}] was seccesfuly", id);
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserResponseDto> searchUsersByBirthDateRange(LocalDate from, LocalDate to) {
    log.trace("Started searchUsersByBirthDateRange from [{}] to [{}]", from, to);

    checkBirthDateRange(from, to);

    return userRepository.findAllByBirthDateBetween(from, to).stream()
        .map(userMapper::toResponseDto)
        .toList();
  }

  private void checkBirthDateRange(LocalDate from, LocalDate to) {
    if (from.isAfter(to)) {
      throw new DateSearchException(
          String.format("User birth date from [%s] mast be lass then to [%s]", from, to));
    }
  }

  private User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("User with id [{}] wasn't found in repository", id);
          throw new UserNotFoundException(
              String.format("User with id [%s] wasn't found in repository", id));
        });
  }

  private void checkingUniqueEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      log.warn("Email [{}] already in use", email);
      throw new EmailNotUniqException(String.format("Email %s already in use", email));
    }
  }
}
