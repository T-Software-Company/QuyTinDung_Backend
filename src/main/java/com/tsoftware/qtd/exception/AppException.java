package com.tsoftware.qtd.exception;

public class AppException extends RuntimeException {

  // Thuộc tính để lưu trữ mã lỗi (ErrorCode)
  private ErrorCode errorCode;

  // Constructor nhận tham số là một ErrorCode và truyền thông báo lỗi vào RuntimeException
  public AppException(ErrorCode errorCode) {
    // Gọi constructor của lớp cha (RuntimeException) với thông báo lỗi từ ErrorCode
    super(errorCode.getMessage());
    // Gán ErrorCode cho thuộc tính errorCode của AppException
    this.errorCode = errorCode;
  }

  // Phương thức getter để lấy mã lỗi
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  // Phương thức setter để thay đổi mã lỗi (nếu cần)
  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
