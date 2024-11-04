package com.tsoftware.qtd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // General errors
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Khóa không hợp lệ", HttpStatus.BAD_REQUEST),

    // Validation errors
    INVALID_USERNAME(1003, "Tên người dùng phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu phải có ít nhất {min} ký tự", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1005, "Vui lòng nhập địa chỉ email", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(1006, "Định dạng email không hợp lệ", HttpStatus.BAD_REQUEST),
    FIRST_NAME_REQUIRED(1007, "Vui lòng nhập tên", HttpStatus.BAD_REQUEST),
    LAST_NAME_REQUIRED(1008, "Vui lòng nhập họ", HttpStatus.BAD_REQUEST),
    ROLE_NAME_REQUIRED(1009, "Vui lòng chọn vai trò", HttpStatus.BAD_REQUEST),
    ROLE_ID_REQUIRED(1010, "Vui lòng cung cấp ID vai trò", HttpStatus.BAD_REQUEST),
    DOB_REQUIRED(1011, "Vui lòng nhập ngày sinh", HttpStatus.BAD_REQUEST),
    DOB_MUST_BE_IN_PAST(1012, "Ngày sinh phải ở trong quá khứ", HttpStatus.BAD_REQUEST),
    GENDER_REQUIRED(1013, "Vui lòng chọn giới tính", HttpStatus.BAD_REQUEST),
    EMPLOYMENT_STATUS_REQUIRED(1014, "Vui lòng chọn trạng thái công việc", HttpStatus.BAD_REQUEST),
    BANNED_STATUS_REQUIRED(1015, "Trạng thái khóa là bắt buộc", HttpStatus.BAD_REQUEST),
    PHONE_REQUIRED(1016, "Vui lòng nhập số điện thoại", HttpStatus.BAD_REQUEST),
    PHONE_TOO_LONG(1017, "Số điện thoại quá dài, tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_FORMAT(1018, "Định dạng số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    ADDRESS_REQUIRED(1019, "Vui lòng nhập địa chỉ", HttpStatus.BAD_REQUEST),
    ADDRESS_TOO_LONG(1020, "Địa chỉ quá dài, tối đa {max} ký tự", HttpStatus.BAD_REQUEST),
    EMPLOYEE_CODE_REQUIRED(1021, "Vui lòng nhập mã nhân viên", HttpStatus.BAD_REQUEST),
    EMPLOYEE_CODE_TOO_LONG(1022, "Mã nhân viên quá dài, tối đa {max} ký tự", HttpStatus.BAD_REQUEST),

    // Authentication & Authorization errors
    UNAUTHENTICATED(1023, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1024, "Bạn không có quyền", HttpStatus.FORBIDDEN),

    // User errors
    EMAIL_EXISTED(1025, "Email đã tồn tại, vui lòng chọn email khác", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1026, "Tên người dùng đã tồn tại, vui lòng chọn tên khác", HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1027, "Vui lòng nhập tên người dùng", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1028, "Người dùng không tồn tại", HttpStatus.BAD_REQUEST),

    // New validation errors (missing translations added)
    USERNAME_REQUIRED(1029, "Vui lòng nhập tên người dùng", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1030, "Vui lòng nhập mật khẩu", HttpStatus.BAD_REQUEST),
    ROLE_NAME_MISSING(1031, "Tên vai trò không được bỏ trống", HttpStatus.BAD_REQUEST),
    ROLE_ID_MISSING(1032, "ID vai trò không được bỏ trống", HttpStatus.BAD_REQUEST),
    DOB_MISSING(1033, "Ngày sinh không được bỏ trống", HttpStatus.BAD_REQUEST),
    GENDER_MISSING(1034, "Giới tính không được bỏ trống", HttpStatus.BAD_REQUEST),
    EMPLOYMENT_STATUS_MISSING(1035, "Trạng thái công việc không được bỏ trống", HttpStatus.BAD_REQUEST),
    BANNED_STATUS_MISSING(1036, "Trạng thái khóa không được bỏ trống", HttpStatus.BAD_REQUEST),
    PHONE_MISSING(1037, "Số điện thoại không được bỏ trống", HttpStatus.BAD_REQUEST),
    ADDRESS_MISSING(1038, "Địa chỉ không được bỏ trống", HttpStatus.BAD_REQUEST),
    EMPLOYEE_CODE_MISSING(1039, "Mã nhân viên không được bỏ trống", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;
}
