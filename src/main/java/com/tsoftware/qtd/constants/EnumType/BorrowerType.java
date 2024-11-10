package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum BorrowerType {
  Personal("Cá nhân"),
  Business("Doanh nghiệp");
  private final String description;

  BorrowerType(String description) {
    this.description = description;
  }
}
