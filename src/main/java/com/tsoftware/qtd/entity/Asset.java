package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Asset extends AbstractAuditEntity {

  private BigDecimal assessedValue;
  private String liquidity;
  private String risk;

  @Enumerated(EnumType.STRING)
  private AssetType assetType;

  @OneToMany private List<LegalDocument> legalDocuments;

  @ManyToOne private Credit credit;

  @OneToOne(mappedBy = "asset")
  private Apartment apartment;

  @OneToOne(mappedBy = "asset")
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "asset")
  private Vehicle vehicle;

  @OneToOne(mappedBy = "asset")
  private MarketStalls marketStalls;

  @OneToOne(mappedBy = "asset")
  private Machinery machinery;

  @OneToOne(mappedBy = "asset")
  private OtherAsset otherAsset;

  @OneToOne(mappedBy = "asset")
  private LandAsset landAsset;

  @ManyToOne private AppraisalPlan appraisalPlan;

  @ManyToOne private AssetRepossessionNotice assetRepossessionNotice;

  @ManyToOne private Customer customer;

  @ManyToOne private ValuationMeeting valuationMeeting;
  @ManyToOne private ValuationReport valuationReport;
}
