package com.tsoftware.qtd.dto.identity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleRepresentation {
  private String id; // ID của vai trò
  private String name; // Tên của vai trò
  private String description; // Mô tả của vai trò
}
