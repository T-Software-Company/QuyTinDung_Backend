package com.tsoftware.qtd.dto.asset;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@Getter
@Setter
@Builder
public class ApartmentRequest {

  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  private String typeOfHousing;
  private String Name;
  private BigDecimal floorArea;
  private String typeOfOwnership;
  private ZonedDateTime ownershipTerm;
  private String notes;
  private String sharedFacilities;
  private String certificateNumber;
  private String certificateBookNumber;
  private String issuingAuthority;
  private ZonedDateTime issueDate;
}
