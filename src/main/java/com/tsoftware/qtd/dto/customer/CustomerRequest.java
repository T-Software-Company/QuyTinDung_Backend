package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerRequest {
  String fullName;
  String email;
  Integer phone;
  String note;
  String signaturePhoto;
  Gender gender;
  String status;
  CMNDDto cmnd;
  CCCDDto cccd;
  PassPortDto passPort;
  AddressDto address;
}
