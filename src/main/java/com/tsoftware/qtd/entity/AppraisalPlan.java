package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime startDate;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
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
  private Application application;

  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @OneToMany(mappedBy = "appraisalPlan", fetch = FetchType.LAZY)
  private List<LoanPurposeDocument> loanPurposeDocuments;

  @OneToOne(mappedBy = "appraisalPlan", fetch = FetchType.LAZY)
  private ValuationReport valuationReport;
}
