package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;

@Getter
public enum PassPortType {
  ORDINARY("Hộ chiếu phổ thông (dành cho công dân thông thường)"),
  DIPLOMATIC("Hộ chiếu ngoại giao (dành cho các nhà ngoại giao)"),
  OFFICIAL("Hộ chiếu công vụ (dành cho nhân viên chính phủ)"),
  SERVICE("Hộ chiếu dịch vụ (dành cho nhân viên công vụ)"),
  TEMPORARY("Hộ chiếu tạm thời (dành cho trường hợp cấp khẩn cấp hoặc ngắn hạn)");

  private final String description;

  PassPortType(String description) {
    this.description = description;
  }
}
