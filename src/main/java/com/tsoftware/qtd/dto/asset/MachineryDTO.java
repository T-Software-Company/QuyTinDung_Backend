package com.tsoftware.qtd.dto.asset;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class MachineryDTO {
  private UUID id;
  private String name;
  private String model;
  private String manufacturer;
  private ZonedDateTime manufactureDate;
  private ZonedDateTime purchaseDate;
  private BigDecimal purchasePrice;
  private String serialNumber;
  private String location;
  private String status;
  private String note;
  private Map<String, Object> metadata;
}
