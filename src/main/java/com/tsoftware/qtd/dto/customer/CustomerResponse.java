package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.address.AddressDto;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse extends AbstractResponse {
  UUID id;
  String username;
  String password;
  String email;
  String code;
  String phone;
  Boolean enabled;
  String firstName;
  String lastName;
  AddressDto address;
  IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
