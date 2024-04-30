package com.cearsolutions.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cearsolutions.dto.request.UpdateUserRequestDto;
import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.DateSearchException;
import com.cearsolutions.exception.EmailNotUniqException;
import com.cearsolutions.exception.UserNotFoundException;
import com.cearsolutions.mapper.UserMapperImpl;
import com.cearsolutions.model.User;
import com.cearsolutions.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * {@link UserServiceImplTest}
 *
 * @author Dmytro Trotsenko on 4/29/24
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void init() {
    UserMapperImpl userMapper = new UserMapperImpl();
    ReflectionTestUtils.setField(userService, "userMapper", userMapper);
  }

  @Test
  @DisplayName("create should create a new user")
  void create_shouldCreateNewUser() {

    // Given
    UserRequestDto userRequestDto = new UserRequestDto(
        "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);
    User user = new User(
        1L, "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);

    when(userRepository.existsByEmail("taras@gmail.com")).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    // When
    UserResponseDto userResponseDto = userService.create(userRequestDto);

    // Then
    assertNotNull(userResponseDto);
    assertEquals(userRequestDto.getEmail(), userResponseDto.getEmail());
    assertEquals(userRequestDto.getFirstName(), userResponseDto.getFirstName());
    assertEquals(userRequestDto.getLastName(), userResponseDto.getLastName());
    assertEquals(userRequestDto.getBirthDate(), userResponseDto.getBirthDate());
  }

  @Test
  @DisplayName("create should throw EmailNotUniqException when email already exists")
  void create_shouldThrowEmailNotUniqExceptionWhenEmailExists() {
    // Given
    UserRequestDto userRequestDto = new UserRequestDto(
        "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);

    when(userRepository.existsByEmail("taras@gmail.com")).thenReturn(true);

    // When / Then
    assertThrows(EmailNotUniqException.class, () -> userService.create(userRequestDto));
  }

  @Test
  @DisplayName("update user should update one field")
  void update_shouldUpdateOneField() {
    // Given
    User user = new User(
        1L, "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    var updateUserRequestDto = new UpdateUserRequestDto();
    updateUserRequestDto.setFirstName("Andriy");

    var updatedUser = userService.updateById(1L, updateUserRequestDto);

    assertNotNull(updatedUser);
    assertEquals("Andriy", updatedUser.getFirstName());
    assertEquals("Shevchenko", updatedUser.getLastName());
  }

  @Test
  @DisplayName("update user should update all fields")
  void update_shouldUpdateAllFields() {
    // Given
    User user = new User(
        1L, "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.save(any(User.class))).thenReturn(user);

    var updateUserRequestDto = new UpdateUserRequestDto(
        "andriy@gmail.com", "Andriy", "Shevchenko", LocalDate.of(1986, 3, 9), null, null);

    var updatedUser = userService.updateById(1L, updateUserRequestDto);

    assertNotNull(updatedUser);
    assertEquals(user.getEmail(), updatedUser.getEmail());
    assertEquals(user.getFirstName(), updatedUser.getFirstName());
    assertEquals(user.getLastName(), updatedUser.getLastName());
    assertEquals(user.getBirthDate(), updatedUser.getBirthDate());
  }

  @Test
  @DisplayName("Delete user - user not found")
  void deleteById_UserNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.deleteById(1L));

    verify(userRepository, times(1)).findById(1L);
    verify(userRepository, never()).deleteById(anyLong());
  }

  @Test
  @DisplayName("Search users by birth date range - success")
  void searchUsersByBirthDateRange_Success() {
    // Given
    LocalDate fromDate = LocalDate.of(1800, 1, 1);
    LocalDate toDate = LocalDate.of(1900, 12, 31);

    User user1 = new User(
        1L, "taras@gmail.com", "Taras", "Shevchenko", LocalDate.of(1814, 3, 9), null, null);
    User user2 = new User(
        1L, "dmytro@gmail.com", "Dmytro", "Trotsenko", LocalDate.of(1989, 12, 10), null, null);

    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);

    when(userRepository.findAllByBirthDateBetween(fromDate, toDate)).thenReturn(
        users.subList(0, 1));

    // When
    List<UserResponseDto> result = userService.searchUsersByBirthDateRange(fromDate, toDate);

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Taras", result.get(0).getFirstName());
    verify(userRepository, times(1)).findAllByBirthDateBetween(fromDate, toDate);
  }

  @Test
  @DisplayName("Search users by birth date range - from date after to date")
  void searchUsersByBirthDateRange_FromDateAfterToDate() {
    // Given
    LocalDate fromDate = LocalDate.of(2024, 1, 1);
    LocalDate toDate = LocalDate.of(1900, 12, 31);

    // When / Then
    assertThrows(DateSearchException.class,
        () -> userService.searchUsersByBirthDateRange(fromDate, toDate));

    // Verify
    verify(userRepository, never()).findAllByBirthDateBetween(any(), any());
  }
}
