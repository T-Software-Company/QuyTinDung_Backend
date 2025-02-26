package com.tsoftware.qtd.dto.asset;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
public class MarketStallsDTO {
  private UUID id;
  private String stallName;
  private String ownerName;
  private String category;
  private BigDecimal areaSize;
  private BigDecimal rentPrice;
  private ZonedDateTime rentStartDate;
  private ZonedDateTime rentEndDate;
  private String location;
  private String contactNumber;
  private boolean isOccupied;
  private String note;
  private Map<String, Object> metadata;
}
