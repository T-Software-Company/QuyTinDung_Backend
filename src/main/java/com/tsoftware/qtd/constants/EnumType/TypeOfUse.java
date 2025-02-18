package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeOfUse {
  COMMON("chung"),
  PRIVATE("riêng");
  private final String description;
}
