package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDTO;
import jakarta.validation.constraints.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
  UUID id;
  String username;
  String password;
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
