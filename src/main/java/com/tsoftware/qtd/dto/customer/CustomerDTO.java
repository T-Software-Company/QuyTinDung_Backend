package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsoftware.qtd.dto.address.AddressDto;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
  AddressDto address;
  IdentityInfoDTO identityInfo;
  String signaturePhoto;
}
