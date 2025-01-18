package com.tsoftware.qtd.dto.customer;

import com.tsoftware.qtd.dto.address.AddressDTO;
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
  private AddressDTO permanentAddress;
  private AddressDTO currentAddress;
}
