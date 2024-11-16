package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationReport extends AbstractAuditEntity {
  private BigDecimal totalValuationAmount;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToMany(mappedBy = "valuationReport")
  private List<Asset> assets;

  @OneToOne(mappedBy = "valuationReport")
  private ValuationMeeting valuationMeeting;

  @OneToOne(mappedBy = "valuationReport")
  private AppraisalReport appraisalReport;

  @OneToOne private AppraisalPlan appraisalPlan;

  @OneToMany(mappedBy = "valuationReport")
  private List<Approve> approves;
}
