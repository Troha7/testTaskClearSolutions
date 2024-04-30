package com.cearsolutions.service;

import com.cearsolutions.dto.request.UpdateUserRequestDto;
import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.DateSearchException;
import com.cearsolutions.exception.EmailNotUniqException;
import com.cearsolutions.exception.UserNotFoundException;
import java.time.LocalDate;
import java.util.List;

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

  /**
   * Update user by id.
   *
   * @param id             user id.
   * @param userRequestDto The user details to be updated.
   * @return The response containing details of the updated user.
   * @throws UserNotFoundException If the provided user wasn't found.
   */
  UserResponseDto updateById(Long id, UpdateUserRequestDto userRequestDto);

  /**
   * Delete user by id.
   *
   * @param id user id.
   * @throws UserNotFoundException If the provided user wasn't found.
   */
  void deleteById(Long id);

  /**
   * Searches for users based on birthdate range.
   *
   * @param from the start date of the birthdate range (inclusive)
   * @param to   the end date of the birthdate range (inclusive)
   * @return a list of UserResponseDto objects matching the birthdate range
   * @throws DateSearchException if the start date is after the end date
   */
  List<UserResponseDto> searchUsersByBirthDateRange(LocalDate from, LocalDate to);
}
