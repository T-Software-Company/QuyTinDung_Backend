package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum Roles {
  ADMIN("Quản trị hệ thống"),
  CREDIT_COMMITTEE("Hội đồng tín dụng"),
  PRICING_COMMITTEE("Hội đồng định giá"),
  EXECUTIVE_BOARD("Ban điều hành"),
  CREDIT_MANAGER("Cấp quản lý tín dụng"),
  ACCOUNTING_MANAGER("Cấp quản lý kế toán"),
  CREDIT_STAFF("Cấp nhân viên tín dụng"),
  ACCOUNTANT("Kế toán");

  private final String description;

  Roles(String description) {
    this.description = description;
  }
}
