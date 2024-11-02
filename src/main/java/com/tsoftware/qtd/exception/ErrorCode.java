package com.tsoftware.qtd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

// Đánh dấu lớp với @Getter để tự động tạo các phương thức get cho các thuộc tính
@Getter
public enum ErrorCode {
    // Định nghĩa các mã lỗi và thông báo lỗi tương ứng dưới dạng hằng số trong enum

    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Khóa không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Tên người dùng phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền", HttpStatus.FORBIDDEN),
    EMAIL_EXISTED(1008, "Email đã tồn tại, vui lòng chọn email khác", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1009, "Tên người dùng đã tồn tại, vui lòng chọn tên khác", HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1010, "Vui lòng nhập tên người dùng", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1011, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST);

    // Constructor của enum để gán giá trị cho các thuộc tính code, message, và statusCode
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;             // Mã số của lỗi
        this.message = message;       // Thông báo lỗi
        this.statusCode = statusCode; // Trạng thái HTTP của lỗi
    }

    private final int code;                  // Mã số của lỗi
    private final HttpStatusCode statusCode; // Trạng thái HTTP tương ứng với lỗi
    private final String message;            // Thông báo chi tiết của lỗi
}
