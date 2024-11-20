package com.tsoftware.qtd.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {

  @NotNull @NotBlank private String country;
  private String cityProvince;
  @NotNull @NotBlank private String district;
  @NotNull @NotBlank private String wardOrCommune;
  @NotNull @NotBlank private String streetAddress;
  @NotNull @NotBlank private String detail;
}
