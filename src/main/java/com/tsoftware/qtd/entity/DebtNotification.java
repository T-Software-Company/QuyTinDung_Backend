package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DebtNotification extends AbstractAuditEntity {

  @ManyToOne private Customer customer;

  @ManyToOne private Loan loan;

  private Long amount;

  private String message;
}
