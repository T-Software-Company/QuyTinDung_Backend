package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoanSecurityType {
  NONE(""),
  UNSECURED("Tín chấp"),
  MORTGAGE("Thế chấp"),
  PLEDGE("Cầm cố");
  private final String description;
}
