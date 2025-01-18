package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSimpleResponse {
  String userId;
  String email;
  String username;
  String firstName;
  String lastName;
  List<Role> roles;
}
