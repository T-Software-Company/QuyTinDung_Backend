package com.tsoftware.qtd.dto.asset;

import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class MarketStallsRequest {

  private String stallName;
  private String ownerName;
  private String category;
  private String areaSize;
  private String rentPrice;
  private ZonedDateTime rentStartDate;
  private ZonedDateTime rentEndDate;
  private String location;
  private String contactNumber;
  private boolean isOccupied;
  private String note;
}
