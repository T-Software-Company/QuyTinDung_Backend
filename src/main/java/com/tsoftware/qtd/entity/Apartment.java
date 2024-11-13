package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Apartment extends AbstractAuditEntity {

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

  @Enumerated(EnumType.STRING)
  private TypeOfUse typeOfUse;

  @OneToOne private Asset asset;

  @OneToOne(cascade = CascadeType.ALL)
  private OwnerInfo ownerInfo;

  @OneToOne(cascade = CascadeType.ALL)
  private TransferInfo transferInfo;
}
