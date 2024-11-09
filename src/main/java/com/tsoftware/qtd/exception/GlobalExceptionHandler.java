package com.tsoftware.qtd.exception;

import com.tsoftware.qtd.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

  private static final String MIN_ATTRIBUTE = "min";

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse<Void>> handlingRuntimeException(Exception exception) {
    log.error("Exception: ", exception);
    ApiResponse<Void> apiResponse = new ApiResponse<Void>();

    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    return ResponseEntity.internalServerError().body(apiResponse);
  }

  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<Void>> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    ApiResponse<Void> apiResponse = new ApiResponse<>();

    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());

    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(
            ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<Object>> handlingValidation(
      MethodArgumentNotValidException exception) {
    String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

    try {
      ErrorCode errorCode = ErrorCode.valueOf(enumKey);
      var constraintViolation =
          exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

      Map<?, ?> attributesObj = constraintViolation.getConstraintDescriptor().getAttributes();
      if (attributesObj != null) {
        Map<String, Object> attributes = new HashMap<>();
        for (Map.Entry<?, ?> entry : attributesObj.entrySet()) {
          if (entry.getKey() instanceof String && entry.getValue() != null) {
            attributes.put((String) entry.getKey(), entry.getValue());
          }
          throw new IllegalArgumentException(
              "Invalid attribute: key must be a String and value must not be null");
        }
        log.info(attributes.toString());
        ApiResponse<Object> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(mapAttribute(errorCode.getMessage(), attributes));

        return ResponseEntity.badRequest().body(apiResponse);
      }
      throw new NullPointerException("Attributes object is null");

    } catch (Exception e) {
      log.error("Cannot find error code for key: {}", enumKey);
      log.error("Message: {}", e.getMessage());
      return ResponseEntity.internalServerError()
          .body(
              new ApiResponse<>(
                  HttpStatus.INTERNAL_SERVER_ERROR.value(),
                  HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                  null));
    }
  }

  private String mapAttribute(String message, Map<String, Object> attributes) {
    String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
    return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
  }
}
