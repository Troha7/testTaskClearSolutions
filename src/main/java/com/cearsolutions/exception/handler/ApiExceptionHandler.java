package com.cearsolutions.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

import com.cearsolutions.dto.response.ApiErrorResponseDto;
import com.cearsolutions.exception.EmailNotUniqException;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * {@link ApiExceptionHandler}
 *
 * @author Dmytro Trotsenko on 4/28/24
 */

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(CONFLICT)
  @ExceptionHandler(EmailNotUniqException.class)
  public ApiErrorResponseDto<String> handleConflictException(RuntimeException exception) {
    return new ApiErrorResponseDto<>(CONFLICT, exception.getMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    List<FieldError> fieldErrors = Objects.requireNonNull(ex.getBindingResult()).getFieldErrors();
    List<String> errorMessages = fieldErrors.stream()
        .map(err -> String.format("Invalid '%s': %s", err.getField(), err.getDefaultMessage()))
        .toList();
    return new ResponseEntity<>(new ApiErrorResponseDto<>(BAD_REQUEST, errorMessages), status);
  }
}
