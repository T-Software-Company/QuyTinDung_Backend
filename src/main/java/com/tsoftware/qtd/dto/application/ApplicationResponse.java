package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.dto.AbstractResponse;
import com.tsoftware.qtd.dto.employee.EmployeeSimpleResponse;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
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
  private LoanSecurityType loanSecurityType;
  private LoanStatus status;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private Integer loanTerm;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
  private Customer customer;
  private Map<String, Object> metadata;
  private String purpose;
  private BorrowerType borrowerType;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Customer {
    private String id;
  }
}
