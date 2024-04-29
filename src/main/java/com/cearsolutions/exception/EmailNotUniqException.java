package com.cearsolutions.exception;

/**
 * {@link EmailNotUniqException}
 *
 * @author Dmytro Trotsenko on 4/27/24
 */
public class EmailNotUniqException extends RuntimeException{

  public EmailNotUniqException(String message) {
    super(message);
  }
}
