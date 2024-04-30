package com.cearsolutions.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.cearsolutions.dto.request.UpdateUserRequestDto;
import com.cearsolutions.dto.request.UserRequestDto;
import com.cearsolutions.dto.response.UserResponseDto;
import com.cearsolutions.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link UserController}
 *
 * @author Dmytro Trotsenko on 4/28/24
 */

@Tag(name = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(path = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public UserResponseDto create(@Valid @RequestBody UserRequestDto userRequestDto) {
    return userService.create(userRequestDto);
  }

  @PutMapping(path = "/update/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public UserResponseDto update(@PathVariable Long id,
      @Valid @RequestBody UpdateUserRequestDto userRequestDto) {
    return userService.updateById(id, userRequestDto);
  }

  @DeleteMapping(path = "/delete/{id}")
  public void delete(@PathVariable Long id) {
    userService.deleteById(id);
  }

  @GetMapping(value = "/search")
  public List<UserResponseDto> searchByBirthDate(
      @RequestParam(name = "from") @DateTimeFormat(iso = ISO.DATE) LocalDate from,
      @RequestParam(name = "to") @DateTimeFormat(iso = ISO.DATE) LocalDate to) {
    return userService.searchUsersByBirthDateRange(from, to);
  }
}
