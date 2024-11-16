package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class AppraisalPlan extends AbstractAuditEntity {
  private String address;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime startDate;

  @Temporal(TemporalType.TIMESTAMP)
  private ZonedDateTime endDate;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToMany(mappedBy = "appraisalPlans", fetch = FetchType.LAZY)
  private List<Employee> participants;

  @OneToMany(mappedBy = "appraisalPlan", fetch = FetchType.LAZY)
  private List<IncomeProof> incomeProof;

  @OneToOne(fetch = FetchType.LAZY)
  private AppraisalReport appraisalReport;

  @OneToOne(fetch = FetchType.LAZY)
  private Credit credit;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToMany(mappedBy = "appraisalPlan", fetch = FetchType.LAZY)
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToOne(mappedBy = "appraisalPlan", fetch = FetchType.LAZY)
  private ValuationReport valuationReport;
}
