package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.employee.EmployeeResponse;
import com.tsoftware.qtd.dto.transaction.WorkflowTransactionResponse;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ApplicationResponse extends AbstractResponse {
  private BigDecimal amount;
  private List<EmployeeResponse> loanProcessors;
  private List<WorkflowTransactionResponse> transactions;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
}
