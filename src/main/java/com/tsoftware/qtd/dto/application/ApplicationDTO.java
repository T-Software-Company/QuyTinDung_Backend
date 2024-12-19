package com.tsoftware.qtd.dto.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tsoftware.qtd.constants.EnumType.ApplicationStep;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.constants.EnumType.LoanStatus;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO {
  private UUID id;
  private BigDecimal amount;
  private ApplicationStep step;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private LoanStatus status;
  private LoanSecurityType loanSecurityType;
  //  private CustomerDTO customer;
  private String customerId;
  private LoanPlanDTO loanPlan;
  private LoanRequestDTO loanRequest;
  private FinancialInfoDTO financialInfo;
  boolean canSign;
}
