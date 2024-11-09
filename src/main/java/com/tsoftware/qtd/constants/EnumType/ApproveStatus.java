package com.tsoftware.qtd.constants.EnumType;

public enum ApproveStatus {
  APPROVED("Chấp thuận"), // Chấp thuận
  REJECTED("Không chấp thuận"); // Không chấp thuận
  private final String description;

  ApproveStatus(String description) {
    this.description = description;
  }
}
