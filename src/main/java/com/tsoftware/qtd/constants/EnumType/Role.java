package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
  ADMIN("Admin"),
  ASSET_VALUATION_ACCESS("Định giá"),
  APPRAISAL("Thẩm định"),
  CREDIT_ACCESS("Tín dụng"),
  REPORT_ACCESS("Báo cáo"),
  EMPLOYEE("Nhân viên"),
  CUSTOMER("Khách hàng");

  private final String description;
}
