package com.cearsolutions.annotation;

import com.cearsolutions.annotation.validator.BirthDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link BirthDateValidation}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateValidation {
  String message() default "Invalid birth date";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
