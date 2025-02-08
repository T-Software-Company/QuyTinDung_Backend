package com.tsoftware.qtd.exception;

import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.turkraft.springfilter.parser.InvalidSyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private static final String INVALID_REQUEST_INFORMATION_MESSAGE =
      "Request information is not valid";
  private final Environment environment;

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception exception) {
    log.error("Exception: ", exception);
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();
    var isProduction = Arrays.asList(environment.getActiveProfiles()).contains("production");
    var message =
        isProduction ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() : exception.getMessage();
    apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    apiResponse.setMessage(message);

    return ResponseEntity.internalServerError().body(apiResponse);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(
      NoResourceFoundException exception) {
    log.error(
        "No resource found: {}, HTTP Method: {}",
        exception.getResourcePath(),
        exception.getHttpMethod());

    ApiResponse<Void> response =
        ApiResponse.<Void>builder()
            .message("Resource not found: " + exception.getResourcePath())
            .code(HttpStatus.NOT_FOUND.value())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException exception) {

    ApiResponse<Void> response =
        ApiResponse.<Void>builder()
            .message("Missing: " + exception.getParameterName() + " parameter")
            .code(HttpStatus.BAD_REQUEST.value())
            .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(WorkflowException.class)
  public ResponseEntity<ApiResponse<Void>> handleWorkflowException(WorkflowException exception) {
    ApiResponse<Void> response =
        ApiResponse.<Void>builder()
            .message(exception.getMessage())
            .code(exception.getStatus())
            .build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(value = KeycloakException.class)
  ResponseEntity<ApiResponse<Void>> handleKeyCloak(KeycloakException exception) {
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
    Map<String, Object> errors = new HashMap<>();
    ex.getAllErrors()
        .forEach(
            e -> {
              if (e instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
              } else {
                errors.put(Objects.requireNonNull(e.getCodes())[0], e.getDefaultMessage());
              }
            });
    ex.getAllValidationResults()
        .forEach(
            result -> {
              result
                  .getResolvableErrors()
                  .forEach(
                      ms -> {
                        if (ms instanceof FieldError fieldError) {
                          errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                        } else {
                          errors.put(
                              result.getMethodParameter().getParameterName()
                                  + Arrays.toString(ms.getCodes()),
                              ms.getDefaultMessage());
                        }
                      });
            });
    return ResponseEntity.badRequest()
        .body(
            new ApiResponse<>(
                ErrorType.REQUEST_BODY_NOT_VALID.getCode(),
                INVALID_REQUEST_INFORMATION_MESSAGE,
                errors));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiResponse<Map<String, Object>>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              errors.put(error.getField(), error.getDefaultMessage());
            });
    return ResponseEntity.badRequest()
        .body(
            ApiResponse.<Map<String, Object>>builder()
                .message(INVALID_REQUEST_INFORMATION_MESSAGE)
                .code(ErrorType.REQUEST_BODY_NOT_VALID.getCode())
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

  @ExceptionHandler(InvalidSyntaxException.class)
  public ResponseEntity<ApiResponse<Object>> handleInvalidSyntaxException(
      InvalidSyntaxException e) {
    return ResponseEntity.badRequest()
        .body(
            new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(), "String filter invalid syntax", e.getMessage()));
  }

  @ExceptionHandler(CommonException.class)
  public ResponseEntity<ApiResponse<?>> handleRuntimeException(CommonException ex) {
    CommonError error = ex.getResponse();
    String message = ex.getMessage();
    var params = ex.getParameters().toArray();
    String errorMessage = String.format(message.replace("{}", "%s"), params);
    return getResponse(error, errorMessage);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    String originalMessage = ex.getMessage();
    // WIP we need check for specific
    Pattern pattern = Pattern.compile("Detail: Key \\((.*?)\\)=\\((.*?)\\) already exists\\.");
    Matcher matcher = pattern.matcher(originalMessage);

    String message = originalMessage;
    Map<String, Object> error = null;
    if (matcher.find()) {
      String field = matcher.group(1); // "code"
      String value = matcher.group(2); // "string"
      message = String.format("Field '%s' with value '%s' already exists.", field, value);
      error = new HashMap<>();
      error.put(field, message);
    }
    return ResponseEntity.badRequest()
        .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, error));
  }

  private ResponseEntity<ApiResponse<?>> getResponse(CommonError error, String message) {
    return new ResponseEntity<>(
        ApiResponse.builder().code(error.getHttpStatus().value()).message(message).build(),
        error.getHttpStatus());
  }
}
