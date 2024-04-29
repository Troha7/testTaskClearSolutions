package com.cearsolutions.service;

import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.EmailNotUniqException;

/**
 * {@link UserService}
 *
 * @author Dmytro Trotsenko on 4/26/24
 */
public interface UserService {

  /**
   * Create a new user.
   *
   * @param userRequestDto The user details to be created.
   * @return The response containing details of the created user.
   * @throws EmailNotUniqException If the provided email is already in use.
   */
  UserResponseDto create(UserRequestDto userRequestDto);
}
