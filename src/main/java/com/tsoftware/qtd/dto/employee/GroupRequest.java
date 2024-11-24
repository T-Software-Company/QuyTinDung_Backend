package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class GroupRequest {
  private String name;
  private List<Role> roles;
}
