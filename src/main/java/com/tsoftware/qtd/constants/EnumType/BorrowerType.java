package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum BorrowerType {
  Personnal("Cá nhân"),
  Corporation("Tập đoàn"),
  Business("Doanh nghiệp");
  private final String description;

  BorrowerType(String description) {
    this.description = description;
  }
}
