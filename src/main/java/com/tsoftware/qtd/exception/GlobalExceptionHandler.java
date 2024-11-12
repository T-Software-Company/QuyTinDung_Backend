package com.tsoftware.qtd.exception;

import com.tsoftware.qtd.dto.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private static final String INVALID_REQUEST_INFORMATION_MESSAGE =
      "Request information is not valid";

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception exception) {
    log.error("Exception: ", exception);
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();

    apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    apiResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

    return ResponseEntity.internalServerError().body(apiResponse);
  }

  @ExceptionHandler(value = NotFoundException.class)
  ResponseEntity<ApiResponse<Void>> handlingRuntimeException(NotFoundException exception) {
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();

    apiResponse.setCode(HttpStatus.NOT_FOUND.value());
    apiResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ApiResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {

    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + " " + error.getDefaultMessage())
            .toList();

    return ResponseEntity.badRequest()
        .body(
            ApiResponse.builder()
                .message(INVALID_REQUEST_INFORMATION_MESSAGE)
                .code(HttpStatus.BAD_REQUEST.value())
                .result(errors)
                .build());
  }
}
