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

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private Apartment apartment;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private LandAndImprovement landAndImprovement;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private Vehicle vehicle;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private MarketStalls marketStalls;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private Machinery machinery;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private OtherAsset otherAsset;

  @OneToOne(mappedBy = "asset", cascade = CascadeType.ALL)
  private LandAsset landAsset;

  @ManyToOne private AppraisalPlan appraisalPlan;

  @ManyToOne private AssetRepossessionNotice assetRepossessionNotice;

  @ManyToOne private Customer customer;

  @ManyToOne private ValuationMeeting valuationMeeting;
  @ManyToOne private ValuationReport valuationReport;
}
