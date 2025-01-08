package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.address.AddressDto;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
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
