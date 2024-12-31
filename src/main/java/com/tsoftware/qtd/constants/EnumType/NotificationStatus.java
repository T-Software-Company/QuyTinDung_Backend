package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum NotificationStatus {
  PENDING("Thông báo nợ đang chờ xử lý, chưa thanh toán"),
  PAID("Thông báo nợ đã được thanh toán đầy đủ"),
  OVERDUE("Thông báo nợ đã quá hạn thanh toán"),
  CANCELED("Thông báo nợ đã bị hủy (nếu có tình huống hủy thông báo"),
  PARTIALLY_PAID("Thông báo đã được thanh toán một phần, còn một số tiền cần thanh toán");
  private final String description;

  NotificationStatus(String description) {
    this.description = description;
  }
}
