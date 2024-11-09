package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.NotificationStatus;
import com.tsoftware.qtd.constants.EnumType.NotificationType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class DebtNotification extends AbstractAuditEntity {

  @ManyToOne
  @JoinColumn(name = "customerId")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "loanId")
  private Loan loan;

  private ZonedDateTime notificationDate;
  private ZonedDateTime dueDate;
  private BigDecimal amountDue;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Enumerated(EnumType.STRING)
  private NotificationStatus notificationStatus;

  private String description;
  private ZonedDateTime createAt;
  private ZonedDateTime updateAt;
}
