package com.tsoftware.qtd.dto.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {

  private String country;
  private String cityProvince;
  private String district;
  private String wardOrCommune;
  private String streetAddress;
  private String detail;
}
