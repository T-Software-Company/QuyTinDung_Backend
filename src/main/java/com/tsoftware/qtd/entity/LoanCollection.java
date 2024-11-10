package com.tsoftware.qtd.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class LoanCollection extends AbstractAuditEntity {

  private BigDecimal amountDue;
  private BigDecimal amountPaid;
  private BigDecimal remainingAmount;
  private ZonedDateTime dueDate;
  private ZonedDateTime paymentDate;
  private String paymentMethod;
  private String status;
  private String note;

  @ManyToOne private Credit credit;
}
