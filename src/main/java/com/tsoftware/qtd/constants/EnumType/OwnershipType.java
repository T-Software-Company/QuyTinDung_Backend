package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OwnershipType {
  INDIVIDUAL, // Sở hữu riêng
  JOINT, // Sở hữu chung
  COMPANY, // Sở hữu doanh nghiệp
  GOVERNMENT // Tài sản nhà nước
}
