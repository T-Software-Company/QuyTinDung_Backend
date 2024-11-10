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

  @OneToOne(mappedBy = "ownerInfo")
  private LandAsset landAsset;

  @OneToOne(mappedBy = "ownerInfo")
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "ownerInfo")
  private Apartment apartment;
}
