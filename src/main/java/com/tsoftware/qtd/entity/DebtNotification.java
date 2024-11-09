package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.NotificationStatus;
import com.tsoftware.qtd.constants.EnumType.NotificationType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DebtNotification extends AbstractAuditEntity {

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private Loan loan;

  private Long amount;

  private String message;
}
