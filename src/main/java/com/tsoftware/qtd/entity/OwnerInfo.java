package com.tsoftware.qtd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime dayOfBirth;

  private String permanentAddress;

  @OneToOne(mappedBy = "ownerInfo", fetch = FetchType.LAZY)
  private LandAsset landAsset;

  @OneToOne(mappedBy = "ownerInfo", fetch = FetchType.LAZY)
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "ownerInfo", fetch = FetchType.LAZY)
  private Apartment apartment;
}
