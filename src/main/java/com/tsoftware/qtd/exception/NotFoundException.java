package com.tsoftware.qtd.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {
  private final String message;

  @Override
  public String getMessage() {
    return message;
  }
}
