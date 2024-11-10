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
public class LandAsset extends AbstractAuditEntity {
  private String plotNumber;
  private String mapNumber;
  private String address;
  private BigDecimal area;
  private String purpose;
  private ZonedDateTime expirationDate;
  private String originOfUsage;

  @Enumerated(EnumType.STRING)
  private TypeOfUse typeOfUse;

  @OneToOne private Asset asset;

  @OneToOne private OwnerInfo ownerInfo;

  @OneToOne private TransferInfo transferInfo;
}
