package com.tsoftware.qtd.dto.application;

import com.tsoftware.commonlib.model.AbstractWorkflowRequest;
import com.tsoftware.qtd.dto.customer.FinancialInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationRequest extends AbstractWorkflowRequest {
  private FinancialInfoDTO financialInfo;
  private LoanPlanDTO loanPlan;
  private LoanRequestDTO loanRequest;
}
