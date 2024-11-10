package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table
public class OwnerInfo extends AbstractAuditEntity {

  private String fullName;
  private ZonedDateTime dayOfBirth;
  private String idCardNumber;
  private String permanentAddress;

  @OneToOne(mappedBy = "transferInfo")
  private LandAsset landAsset;

  @OneToOne(mappedBy = "transferInfo")
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "transferInfo")
  private Apartment apartment;
}
