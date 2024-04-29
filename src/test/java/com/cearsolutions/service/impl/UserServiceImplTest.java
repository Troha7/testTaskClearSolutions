package com.cearsolutions.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.exception.EmailNotUniqException;
import com.cearsolutions.mapper.UserMapperImpl;
import com.cearsolutions.model.User;
import com.cearsolutions.repository.UserRepository;
import java.time.LocalDate;
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
}
