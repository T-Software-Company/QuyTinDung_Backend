package com.tsoftware.qtd.dto.asset;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApartmentDTO {
  private UUID id;
  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  private String typeOfHousing;
  private String name;
  private BigDecimal floorArea;
  private String typeOfOwnership;
  private ZonedDateTime ownershipTerm;
  private String notes;
  private String sharedFacilities;
  private String certificateNumber;
  private String certificateBookNumber;
  private String issuingAuthority;
  private ZonedDateTime issueDate;
  private Map<String, Object> metadata;
  private OwnerInfoDTO ownerInfo;

  private TransferInfoDTO transferInfo;
}
