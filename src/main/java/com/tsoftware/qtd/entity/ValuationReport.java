package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ValuationReport extends AbstractAuditEntity {
  private BigDecimal totalValuationAmount;

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
