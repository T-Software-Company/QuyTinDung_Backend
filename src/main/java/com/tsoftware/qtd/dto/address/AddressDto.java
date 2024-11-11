package com.tsoftware.qtd.dto.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {
  private String streetAddress;
  private String wardOrCommune;
  private String district;
  private String cityProvince;
  private String country;
}
