package com.tsoftware.qtd.exception;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.turkraft.springfilter.parser.InvalidSyntaxException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
  protected ResponseEntity<ApiResponse<Map<String, Object>>> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              addNestedFieldError(errors, error.getField(), error.getDefaultMessage());
            });
    return ResponseEntity.badRequest()
        .body(
            ApiResponse.<Map<String, Object>>builder()
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
      SpringFilterBadRequestException e, HttpServletRequest request) {
    String filter = request.getParameter("filter");
    return ResponseEntity.badRequest()
        .body(
            new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(), "String filter is bad: " + filter, null));
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

    Pattern pattern = Pattern.compile("Detail: Key \\((.*?)\\)=\\((.*?)\\) already exists\\.");
    Matcher matcher = pattern.matcher(originalMessage);

    String message = "Duplicate key value found.";
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

  private void addNestedFieldError(Map<String, Object> errors, String field, String errorMessage) {
    String[] parts = field.split("\\.");
    Map<String, Object> current = errors;

    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      current = handleFieldPart(current, part, i == parts.length - 1, errorMessage);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> handleFieldPart(
      Map<String, Object> current, String part, boolean isFinalField, String errorMessage) {

    if (part.matches("\\w+\\[\\d+]")) {
      String arrayKey = part.substring(0, part.indexOf("["));
      int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));

      current.putIfAbsent(arrayKey, new ArrayList<>());
      List<Object> array = (List<Object>) current.get(arrayKey);
      while (array.size() <= index) {
        array.add(new HashMap<>());
      }

      current = (Map<String, Object>) array.get(index);

      if (isFinalField) {
        array.set(index, errorMessage);
      }
    } else {
      current.putIfAbsent(part, new HashMap<>());
      if (isFinalField) {
        current.put(part, errorMessage);
      } else {
        current = (Map<String, Object>) current.get(part);
      }
    }
    return current;
  }
}
