// Lớp này được dùng để xử lý các trường hợp khi yêu cầu chưa được xác thực hoặc không hợp lệ.

package com.tsoftware.qtd.configuration;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Khởi tạo ObjectMapper để chuyển đổi đối tượng Java thành JSON.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Phương thức commence sẽ được gọi khi một yêu cầu không có xác thực hợp lệ.
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        // Xác định mã lỗi liên quan đến lỗi xác thực (UNAUTHENTICATED).
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        // Thiết lập mã trạng thái HTTP của phản hồi dựa trên mã lỗi.
        response.setStatus(errorCode.getStatusCode().value());

        // Thiết lập Content-Type của phản hồi là JSON để trả về dữ liệu dưới dạng JSON.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Tạo một đối tượng ApiResponse chứa mã lỗi và thông báo lỗi.
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        // Ghi đối tượng ApiResponse dưới dạng JSON vào phản hồi HTTP.
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

        // Đảm bảo rằng dữ liệu đã được gửi đến máy khách.
        response.flushBuffer();
    }
}
