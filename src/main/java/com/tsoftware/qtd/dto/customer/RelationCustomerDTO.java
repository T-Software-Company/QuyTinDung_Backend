package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDto;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RelationCustomerDTO {
  private String fullName;
  private ZonedDateTime dateOfBirth;
  private String identityId;
  private String phone;
  private String email;
  private AddressDto permanentAddress;
  private AddressDto currentAddress;
}
