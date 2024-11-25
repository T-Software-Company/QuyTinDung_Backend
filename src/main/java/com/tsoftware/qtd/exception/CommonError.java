package com.tsoftware.qtd.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

public interface CommonError extends Serializable {

  HttpStatus getHttpStatus();

  int getCode();

  String name();

  String getMessage();
}
