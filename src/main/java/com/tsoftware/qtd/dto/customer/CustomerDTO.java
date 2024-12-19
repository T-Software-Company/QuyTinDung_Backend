package com.tsoftware.qtd.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
  @NotNull String fullName;
  @Email String email;
  String phone;
  String note;
  Gender gender;
  String status;
  @NotNull IdentityInfoDTO identityInfo;
  AddressDto address;
  String signaturePhoto;
}
