package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanPlan extends AbstractAuditEntity {

  private String loanNeeds;
  private BigDecimal totalCapitalRequirement;
  private BigDecimal ownCapital;
  private BigDecimal proposedLoanAmount;
  private BigDecimal monthlyIncome;
  private String repaymentPlan;
  private String note;
  private String loanTerm;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @OneToOne(fetch = FetchType.LAZY)
  private Application application;
}
