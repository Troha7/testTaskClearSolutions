package com.cearsolutions.annotation.validator;

import com.cearsolutions.annotation.BirthDateValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link BirthDateValidator}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@Setter
public class BirthDateValidator implements ConstraintValidator<BirthDateValidation, LocalDate> {

  @Value("${user.min-age}")
  private int minAge;

  @Override
  public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    if (birthDate == null) {
      return true;
    } else if (!birthDate.isBefore(LocalDate.now())) {
      context
          .buildConstraintViolationWithTemplate("The birth date must be earlier than current date")
          .addConstraintViolation();
      return false;
    } else if (Period.between(birthDate, LocalDate.now()).getYears() < minAge) {
      context
          .buildConstraintViolationWithTemplate(
              String.format("The user age must be more than [%s] years old", minAge))
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
