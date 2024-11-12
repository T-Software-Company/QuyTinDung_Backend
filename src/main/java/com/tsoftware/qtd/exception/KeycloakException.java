package com.tsoftware.qtd.exception;

public class KeycloakException extends RuntimeException {
  public KeycloakException(int status) {
    super("KeycloakError: " + status);
  }
}
