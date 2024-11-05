package com.tsoftware.qtd.dto.identity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleRepresentation {
  private String id;
  private String name;
  private String description;
}
