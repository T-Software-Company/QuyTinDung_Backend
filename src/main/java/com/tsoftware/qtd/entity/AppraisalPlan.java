package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

  @ManyToMany(mappedBy = "appraisalPlans")
  private List<Employee> participants;

  @OneToMany(mappedBy = "appraisalPlan")
  private List<IncomeProof> incomeProof;

  @OneToOne private AppraisalReport appraisalReport;

  @OneToOne private Credit credit;

  @ManyToOne private Customer customer;

  @OneToMany(mappedBy = "appraisalPlan")
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToOne(mappedBy = "appraisalPlan")
  private ValuationReport valuationReport;
}
