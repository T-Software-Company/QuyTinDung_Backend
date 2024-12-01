package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

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
  private Boolean valuationStatus;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private AssetType assetType;

  @OneToMany(fetch = FetchType.EAGER)
  private List<LegalDocument> legalDocuments;

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

  @ManyToOne(fetch = FetchType.LAZY)
  private AppraisalPlan appraisalPlan;

  @ManyToOne(fetch = FetchType.LAZY)
  private AssetRepossessionNotice assetRepossessionNotice;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  private ValuationMeeting valuationMeeting;

  @ManyToOne(fetch = FetchType.LAZY)
  private ValuationReport valuationReport;
}
