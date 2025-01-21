package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.address.AddressDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse extends AbstractResponse {
  String username;
  String email;
  String code;
  String phone;
  Boolean enabled;
  String firstName;
  String lastName;
  AddressDTO address;
  IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
