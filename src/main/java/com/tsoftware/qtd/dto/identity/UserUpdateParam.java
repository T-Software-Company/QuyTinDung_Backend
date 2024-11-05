package com.tsoftware.qtd.dto.identity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateParam {
  private String firstName;
  private String lastName;
}
