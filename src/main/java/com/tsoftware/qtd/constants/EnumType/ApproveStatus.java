package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApproveStatus {
  APPROVED("Chấp thuận"),
  REJECTED("Không chấp thuận");
  private final String description;
}
