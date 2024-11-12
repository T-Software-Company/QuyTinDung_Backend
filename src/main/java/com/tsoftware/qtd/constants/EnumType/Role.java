package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
  ADMIN("Admin"),
  ASSET_VALUATION_ACCESS("Định giá"),
  CREDIT_ACCESS("Tín dụng"),
  REPORT_ACCESS("Báo cáo");
  private final String description;

  //  ADMIN("Quản trị hệ thống", Set.of(ASSET_VALUATION_ACCESS, CREDIT_ACCESS, REPORT_ACCESS)),
  //  CREDIT_COMMITTEE("Hội đồng tín dụng", Set.of(CREDIT_ACCESS, REPORT_ACCESS)),
  //  PRICING_COMMITTEE("Hội đồng định giá", Set.of(ASSET_VALUATION_ACCESS, REPORT_ACCESS)),
  //  EXECUTIVE_BOARD("Ban điều hành", Set.of(ASSET_VALUATION_ACCESS, CREDIT_ACCESS,
  // REPORT_ACCESS)),
  //  CREDIT_MANAGER("Cấp quản lý tín dụng", Set.of(CREDIT_ACCESS, REPORT_ACCESS)),
  //  ACCOUNTING_MANAGER("Cấp quản lý kế toán", Set.of(REPORT_ACCESS)),
  //  CREDIT_STAFF("Cấp nhân viên tín dụng", Set.of(CREDIT_ACCESS, REPORT_ACCESS)),
  //  ACCOUNTANT("Kế toán", Set.of(REPORT_ACCESS));
}
