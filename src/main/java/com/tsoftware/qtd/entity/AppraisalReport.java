package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
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
public class AppraisalReport extends AbstractAuditEntity {

  private String title;
  private String note;

  @Enumerated(EnumType.STRING)
  private ApproveStatus approveStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToOne(mappedBy = "appraisalReport", fetch = FetchType.LAZY)
  private AppraisalPlan appraisalPlan;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToOne(fetch = FetchType.LAZY)
  private ValuationReport valuationReport;

  @OneToMany(mappedBy = "appraisalReport")
  private List<Approve> approves;

  @OneToOne(cascade = CascadeType.ALL)
  private CreditRating creditRating;

  @OneToMany(fetch = FetchType.LAZY)
  private List<IncomeProof> incomeProofs;
}
