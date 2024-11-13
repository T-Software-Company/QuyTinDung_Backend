package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
public class TransferInfo extends AbstractAuditEntity {

  private String fullName;
  private ZonedDateTime dayOfBirth;
  private String idCardNumber;
  private String permanentAddress;
  private ZonedDateTime transferDate;
  private String transferRecordNumber;

  @OneToOne(mappedBy = "transferInfo", fetch = FetchType.LAZY)
  private LandAsset landAsset;

  @OneToOne(mappedBy = "transferInfo", fetch = FetchType.LAZY)
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "transferInfo", fetch = FetchType.LAZY)
  private Apartment apartment;
}
