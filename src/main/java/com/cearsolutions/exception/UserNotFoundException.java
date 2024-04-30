package com.cearsolutions.exception;

/**
 * {@link UserNotFoundException}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */
public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(String message) {
    super(message);
  }
}
