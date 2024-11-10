package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TypeOfUse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class LandAsset extends AbstractAuditEntity {
  private String plotNumber;
  private String mapNumber;
  private String address;
  private String area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;
  
  
  @Enumerated(EnumType.STRING)
  private TypeOfUse typeOfUse;
  
  
  @OneToOne
  private Asset asset;
  
  @OneToOne
  private OwnerInfo ownerInfo;
  
  @OneToOne
  private TransferInfo transferInfo;
}
