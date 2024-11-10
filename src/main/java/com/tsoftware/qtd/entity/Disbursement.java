package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
@SuperBuilder
public class Disbursement extends AbstractAuditEntity {

  private BigDecimal loanLimit;
  private BigDecimal amountReceived;
  private BigDecimal currentOutstandingDebt;
  private ZonedDateTime dateOfLoanReceipt;
  private ZonedDateTime loanTerm;
  private BigDecimal interestRate;
  private String repaymentSchedule;

  @ManyToOne private Customer customer;

  @ManyToOne private Credit credit;
}
