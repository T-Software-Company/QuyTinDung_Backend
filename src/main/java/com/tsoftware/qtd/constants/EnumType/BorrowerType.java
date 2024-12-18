package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum BorrowerType {
  INDIVIDUAL("Cá nhân"),
  CORPORATE("Doanh nghiệp");
  private final String description;

  BorrowerType(String description) {
    this.description = description;
  }
}
