package com.wigell.membersproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFormatException extends RuntimeException {

  private final String object;
  private final String field;
  private final String value;

  public InvalidFormatException(String object, String field, String value) {
    super(String.format("Invalid format for %s.%s: '%s'", object, field, value));
    this.object = object;
    this.field = field;
    this.value = value;
  }

  public String getObject() {
    return object;
  }

  public String getField() {
    return field;
  }

  public String getValue() {
    return value;
  }
}
