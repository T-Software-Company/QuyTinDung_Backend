package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum Permissions {
  ASSET_VALUATION_ACCESS("Định giá"),
  CREDIT_ACCESS("Tín dụng"),
  REPORT_ACCESS("Báo cáo");

  private final String description;

  Permissions(String description) {
    this.description = description;
  }
}
