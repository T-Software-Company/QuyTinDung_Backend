package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum LoanCollateralType {
  LAND_USE_RIGHT("Quyền sử dụng đất"),
  HOUSE_OWNERSHIP("Quyền sở hữu nhà ở"),
  ATTACHED_ASSET_OWNERSHIP("Quyền sở hữu tài sản khác gắn liền với đất"),
  APARTMENT("Căn hộ"),
  CAR_REGISTRATION_CERTIFICATE("Giấy chứng nhận đăng kí xe ô tô"),
  MOTORBIKE_REGISTRATION_CERTIFICATE("Giấy chứng nhận đăng kí xe máy");

  private final String description;

  LoanCollateralType(String description) {
    this.description = description;
  }
}
