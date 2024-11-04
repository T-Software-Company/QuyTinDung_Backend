package com.tsoftware.qtd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleResponse {
  private String id;
  private String name;
  private String description;
}
