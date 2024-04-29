package com.cearsolutions.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * {@link ApiErrorResponseDto}
 *
 * @author Dmytro Trotsenko on 4/28/24
 */

@Getter
@RequiredArgsConstructor
public class ApiErrorResponseDto <T> {

  private final HttpStatus httpStatus;
  private final T message;
}
