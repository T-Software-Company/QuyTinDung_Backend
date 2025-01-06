package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum BorrowerType {
  INDIVIDUAL("Cá nhân"),
  ORGANIZATION("Tổ chức");
  private final String description;

  BorrowerType(String description) {
    this.description = description;
  }
}
