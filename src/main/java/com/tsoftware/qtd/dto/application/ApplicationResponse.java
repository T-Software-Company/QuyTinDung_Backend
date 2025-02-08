package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.employee.EmployeeSimpleResponse;
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
  private List<EmployeeSimpleResponse> loanProcessors;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private Integer loanTerm;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
  private Customer customer;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Customer {
    private String id;
  }
}
