package com.tsoftware.qtd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType implements CommonError {
  UNEXPECTED(0, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error. {}"),
  CHECKSUM_INVALID(1, HttpStatus.BAD_REQUEST, "Checksum is invalid."),
  ENTITY_NOT_FOUND(2, HttpStatus.NOT_FOUND, "Entity {} not found."),
  FAIL_TO_CALL_API(4, HttpStatus.INTERNAL_SERVER_ERROR, "Fail to call API {}."),
  RUN_ASYNC_FAIL(6, HttpStatus.INTERNAL_SERVER_ERROR, "Run async fail."),
  DUPLICATED_REQUEST(8, HttpStatus.CONFLICT, "Duplicated request. {}"),
  METHOD_ARGUMENT_NOT_VALID(9, HttpStatus.BAD_REQUEST, "Method argument not valid: {}."),
  REQUEST_BODY_NOT_VALID(10, HttpStatus.BAD_REQUEST, "Request body not valid. {}"),
  FAIL_TO_CONVERT(11, HttpStatus.INTERNAL_SERVER_ERROR, "Fail to convert {} to {}."),
  ACCESS_DENIED(12, HttpStatus.FORBIDDEN, "Access denied. {}"),
  HAS_APPLICATION_IN_PROGRESS(13, HttpStatus.BAD_REQUEST, "Existing in-progress application."),
  CANNOT_SIGN(14, HttpStatus.BAD_REQUEST, "Cannot sign this application, please check again.");

  private final int code;
  private final HttpStatus httpStatus;
  private final String message;
}
