package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.constants.EnumType.Gender;
import com.tsoftware.qtd.dto.address.AddressDto;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerResponse {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  String fullName;
  String email;
  String phone;
  String note;
  String signaturePhoto;
  Gender gender;
  String status;
  CMNDDto cmnd;
  CCCDDto cccd;
  PassPortDto passPort;
  AddressDto address;
}
