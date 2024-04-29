package com.cearsolutions.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@link UserResponseDto}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDto {

  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  private LocalDate birthDate;

  private String address;

  private String phone;
}
