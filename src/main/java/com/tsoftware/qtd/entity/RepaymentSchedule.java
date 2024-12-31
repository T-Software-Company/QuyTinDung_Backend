package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.RepaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RepaymentSchedule extends AbstractAuditEntity {

  @ManyToOne private LoanAccount loanAccount;

  private Integer installmentNumber;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime dueDate;

  private BigDecimal principalAmount;
  private BigDecimal interestAmount;
  private BigDecimal totalAmount;
  private RepaymentStatus status;
}
