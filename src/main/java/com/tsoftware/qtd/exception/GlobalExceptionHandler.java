package com.tsoftware.qtd.exception;

import com.tsoftware.commonlib.model.ApiResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

  @ExceptionHandler(value = KeycloakException.class)
  ResponseEntity<ApiResponse<Void>> handleKeyCloak(KeycloakException exception) {
    log.error("KeycloakException: ", exception);
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();

    apiResponse.setCode(exception.getStatus());
    apiResponse.setMessage(exception.getMessage());

    return ResponseEntity.status(exception.getStatus()).body(apiResponse);
  }

  @ExceptionHandler(value = NotFoundException.class)
  ResponseEntity<ApiResponse<Void>> handlingRuntimeException(NotFoundException exception) {
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();

    apiResponse.setCode(HttpStatus.NOT_FOUND.value());
    apiResponse.setMessage(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(
            ApiResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .result(
                    new ApiResponse<>(HttpStatus.FORBIDDEN.value(), exception.getMessage(), null))
                .build());
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  protected ResponseEntity<ApiResponse<?>> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex) {

    List<String> errors =
        ex.getAllErrors().stream()
            .map(
                error -> {
                  if (error instanceof FieldError fieldError) {
                    return fieldError.getField() + " " + fieldError.getDefaultMessage();
                  }
                  return error.getDefaultMessage();
                })
            .toList();

    return ResponseEntity.badRequest()
        .body(
            new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(), INVALID_REQUEST_INFORMATION_MESSAGE, errors));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              errors.put(error.getField(), error.getDefaultMessage());
            });
    return ResponseEntity.badRequest()
        .body(
            ApiResponse.<Map<String, String>>builder()
                .message(INVALID_REQUEST_INFORMATION_MESSAGE)
                .code(HttpStatus.BAD_REQUEST.value())
                .result(errors)
                .build());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<Object>> handleInvalidJson() {
    String errorMessage = "Invalid request body";
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), errorMessage, null));
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ApiResponse<Object>> handleHHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException e) {
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
  }

  @ExceptionHandler(SpringFilterBadRequestException.class)
  public ResponseEntity<ApiResponse<Object>> handleSpringFilterBadRequestException(
      SpringFilterBadRequestException e) {
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "String filter is bad", null));
  }

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ApiResponse<?>> handleRuntimeException(CommonException ex) {
    CommonError error = ex.getResponse();
    String message = ex.getMessage();
    var params = ex.getParameters().toArray();
    String errorMessage = String.format(message.replace("{}", "%s"), params);
    return getResponse(error, errorMessage);
  }

  private ResponseEntity<ApiResponse<?>> getResponse(CommonError error, String message) {
    return new ResponseEntity<>(
        ApiResponse.builder().code(error.getHttpStatus().value()).message(message).build(),
        error.getHttpStatus());
  }
}
