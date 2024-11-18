package com.tsoftware.qtd.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakException extends RuntimeException {
  private int status;
  private String message;

  public KeycloakException(int status, String message) {
    super(message);
    this.status = status;
    this.message = message;
  }
}
