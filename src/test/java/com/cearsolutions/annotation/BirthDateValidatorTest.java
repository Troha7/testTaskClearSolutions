package com.cearsolutions.annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cearsolutions.annotation.validator.BirthDateValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link BirthDateValidatorTest}
 *
 * @author Dmytro Trotsenko on 4/28/24
 */
public class BirthDateValidatorTest {

  public BirthDateValidator birthDateValidator;
  public ConstraintValidatorContext context;
  @Value("${user.min-age}")
  private int minAge;

  @BeforeEach
  public void setUp() {
    birthDateValidator = new BirthDateValidator();
    birthDateValidator.setMinAge(minAge);

    context = Mockito.mock(ConstraintValidatorContext.class);

    Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
        .thenAnswer(
            (Answer<ConstraintViolationBuilder>)
                invocation -> {
                  ConstraintValidatorContext.ConstraintViolationBuilder builder =
                      Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
                  Mockito.when(builder.addConstraintViolation()).thenReturn(context);
                  return builder;
                });
  }

  @DisplayName("BirthDateValidator should pass validator when user input correct birth date")
  @Test
  public void shouldPassValidator_whenUserInputCorrectBirthDate() {
    // Given
   LocalDate birthDate = LocalDate.of(1989, 12, 10);

    // When
    var isValid = birthDateValidator.isValid(birthDate, context);

    // Then
    assertTrue(isValid);
  }

  @DisplayName("BirthDateValidator should fail validator when user input null")
  @Test
  public void shouldFailValidator_whenUserInputNull() {

    // When
    var isValid = birthDateValidator.isValid(null, context);

    // Then
    assertFalse(isValid);
  }

  @DisplayName("BirthDateValidator should fail validator when user birth date later than current date")
  @Test
  public void shouldFailValidator_whenUserBirthDateLaterCurrentDate() {
    // Given
    LocalDate birthDate = LocalDate.now().plusDays(1);

    // When
    var isValid = birthDateValidator.isValid(birthDate, context);

    // Then
    assertFalse(isValid);
  }

  @DisplayName("BirthDateValidator should fail validator when user age lass then min age")
  @Test
  public void shouldFailValidator_whenUserAgeLassThenMin() {
    // Given
    LocalDate birthDate = LocalDate.now().minusYears(minAge - 1);
    // When
    var isValid = birthDateValidator.isValid(birthDate, context);

    // Then
    assertFalse(isValid);
  }
}
