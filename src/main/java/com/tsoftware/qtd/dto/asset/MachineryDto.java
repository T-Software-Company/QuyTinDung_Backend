package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class MachineryDto {
  private String name;
  private String model;
  private String manufacturer;
  private ZonedDateTime manufactureDate;
  private ZonedDateTime purchaseDate;
  private String purchasePrice;
  private String serialNumber;
  private String location;
  private String status;
  private String note;
}
