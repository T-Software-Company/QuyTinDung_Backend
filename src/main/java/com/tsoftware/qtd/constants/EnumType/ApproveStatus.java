package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApproveStatus {
  WAIT("Chờ phê duyệt"),
  APPROVED("Chấp thuận"),
  REJECTED("Không chấp thuận");
  private final String description;
}
