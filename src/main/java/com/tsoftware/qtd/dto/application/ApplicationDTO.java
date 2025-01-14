package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.dto.customer.CustomerDTO;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
  private UUID id;
  private BigDecimal amount;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private LoanStatus status;
  private LoanSecurityType loanSecurityType;
  private CustomerDTO customer;
  private String customerId;
  private LoanPlanRequest loanPlan;
  private LoanRequestRequest loanRequest;
  private FinancialInfoDTO financialInfo;
  boolean canSign;
}
