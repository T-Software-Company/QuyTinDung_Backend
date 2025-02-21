package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.OwnershipType;
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

  private BigDecimal proposedValue;
  private Boolean valuationStatus;
  private String title;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private AssetType assetType;

  @Enumerated(EnumType.ORDINAL)
  private OwnershipType ownershipType;

  private List<String> documents;

  @ManyToOne private Application application;

  @OneToOne(mappedBy = "asset")
  private ValuationAsset valuationAsset;

  @OneToOne(cascade = CascadeType.ALL)
  private Apartment apartment;

  @OneToOne(cascade = CascadeType.ALL)
  private LandAndImprovement landAndImprovement;

  @OneToOne(cascade = CascadeType.ALL)
  private Vehicle vehicle;

  @OneToOne(cascade = CascadeType.ALL)
  private MarketStalls marketStalls;

  @OneToOne(cascade = CascadeType.ALL)
  private Machinery machinery;

  @OneToOne(cascade = CascadeType.ALL)
  private OtherAsset otherAsset;

  @OneToOne(cascade = CascadeType.ALL)
  private LandAsset landAsset;
}
