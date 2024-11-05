package com.tsoftware.qtd.exception;

import com.tsoftware.qtd.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Đánh dấu lớp này là một Global Exception Handler trong Spring
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  // Tên của thuộc tính "min" được sử dụng trong kiểm tra ràng buộc cho các giá trị tối thiểu
  private static final String MIN_ATTRIBUTE = "min";

  // Xử lý ngoại lệ chung (Exception), để ghi log và trả về phản hồi chuẩn với mã lỗi chung
  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
    log.error("Exception: ", exception); // Ghi lại lỗi chi tiết vào log
    ApiResponse apiResponse = new ApiResponse();

    // Thiết lập mã và thông báo lỗi cho các ngoại lệ không xác định
    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    return ResponseEntity.badRequest().body(apiResponse); // Trả về phản hồi HTTP 400
  }

  // Xử lý ngoại lệ tùy chỉnh (AppException) khi gặp các lỗi trong ứng dụng
  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode(); // Lấy mã lỗi từ AppException
    ApiResponse apiResponse = new ApiResponse();

    // Gán mã và thông báo lỗi dựa trên AppException
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());

    // Trả về phản hồi HTTP với mã lỗi phù hợp
    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }

  // Xử lý ngoại lệ quyền truy cập (AccessDeniedException) khi người dùng không có quyền
  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    // Trả về phản hồi với mã lỗi không đủ quyền (FORBIDDEN)
    return ResponseEntity.status(errorCode.getStatusCode())
        .body(
            ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
  }

  // Xử lý ngoại lệ xác thực tham số (MethodArgumentNotValidException) khi dữ liệu không hợp lệ
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
    String enumKey = exception.getFieldError().getDefaultMessage();

    ErrorCode errorCode = ErrorCode.INVALID_KEY; // Mặc định mã lỗi khi tham số không hợp lệ
    Map<String, Object> attributes = null;
    try {
      errorCode = ErrorCode.valueOf(enumKey); // Tìm mã lỗi tương ứng trong ErrorCode enum

      // Lấy thông tin chi tiết về lỗi từ ConstraintViolation
      var constraintViolation =
          exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

      attributes =
          constraintViolation
              .getConstraintDescriptor()
              .getAttributes(); // Lấy các thuộc tính ràng buộc
      log.info(attributes.toString());

    } catch (IllegalArgumentException e) {
      // Không có lỗi trong trường hợp không tìm thấy enum key
    }

    ApiResponse apiResponse = new ApiResponse();

    // Đặt thông báo lỗi tùy chỉnh hoặc sử dụng thông báo lỗi mặc định từ ErrorCode
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(
        Objects.nonNull(attributes)
            ? mapAttribute(
                errorCode.getMessage(), attributes) // Thay thế thuộc tính "min" vào thông báo lỗi
            : errorCode.getMessage());

    return ResponseEntity.badRequest().body(apiResponse); // Trả về phản hồi HTTP 400
  }

  // Thay thế {min} trong thông báo lỗi với giá trị tối thiểu từ thuộc tính ràng buộc
  private String mapAttribute(String message, Map<String, Object> attributes) {
    String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
    return message.replace("{" + MIN_ATTRIBUTE + "}", minValue); // Thay thế giá trị vào thông báo
  }
}
