package com.cearsolutions.dto.request;

import com.cearsolutions.annotation.BirthDateValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@link UserRequestDto}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDto {

  @NotEmpty(message = "Email cannot be empty")
  @NotNull(message = "Email should not be null")
  @Size(min = 6, max = 255, message = "Email length should be from 6 to 255 symbols")
  @Pattern(regexp = "[A-Za-z0-9.\\-_]+@[A-Za-z]+\\.[A-Za-z]{2,3}", message = "Email format mast be [abc@gmail.com]")
  @Schema(description = "User email", example = "shevchenkoTaras@gmail.com")
  private String email;

  @NotEmpty(message = "First name cannot be empty")
  @NotNull(message = "First name should not be null")
  @Size(min = 3, max = 50, message = "First name length should be from 3 to 50 symbols")
  @Schema(description = "User first name", example = "Taras")
  private String firstName;

  @NotEmpty(message = "Last name cannot be empty")
  @NotNull(message = "Last name should not be null")
  @Size(min = 3, max = 50, message = "Last name length should be from 3 to 50 symbols")
  @Schema(description = "User last name", example = "Shevchenko")
  private String lastName;

  @BirthDateValidation
  @Schema(description = "User birth date", example = "1814-03-09")
  private LocalDate birthDate;

  @NotEmpty(message = "Address cannot be empty")
  @Schema(description = "User address", example = "Moryntsi, Kiev Governorate")
  private String address;

  @NotEmpty(message = "Phone cannot be empty")
  @Pattern(regexp = "\\d{10}", message = "Phone format mast be [0991111111]")
  @Schema(description = "User phone", example = "0991234567")
  private String phone;
}
