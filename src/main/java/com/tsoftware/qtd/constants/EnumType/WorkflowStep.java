package com.tsoftware.qtd.constants.EnumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkflowStep {
  CREATE_LOAN_REQUEST("create-loan-request"),
  CREATE_LOAN_PLAN("create-loan-plan"),
  CREATE_FINANCIAL_INFO("create-financial-info"),
  ;
  private final String description;
}
