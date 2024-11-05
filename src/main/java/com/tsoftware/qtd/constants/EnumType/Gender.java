package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum Gender {
  MALE("Nam"),
  FEMALE("Nữ");
  private final String description;

  Gender(String description) {
    this.description = description;
  }
}
