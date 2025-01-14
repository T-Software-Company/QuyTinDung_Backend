package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.address.AddressDto;
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
  AddressDto address;
  IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
