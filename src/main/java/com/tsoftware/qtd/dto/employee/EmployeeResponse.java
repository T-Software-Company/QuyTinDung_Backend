package com.tsoftware.qtd.dto.employee;

import com.tsoftware.qtd.constants.EnumType.Role;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.address.AddressDTO;
import com.tsoftware.qtd.dto.customer.IdentityInfoDTO;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EmployeeResponse extends AbstractResponse {

  String userId;
  String email;
  private String avatarUrl;
  String username;
  String firstName;
  String lastName;
  List<Role> roles;
  String phone;
  AddressDTO address;
  IdentityInfoDTO identityInfo;
  String code;
  Boolean enabled;
}
