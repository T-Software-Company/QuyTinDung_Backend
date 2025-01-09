package com.tsoftware.qtd.constants.EnumType;

import com.tsoftware.qtd.executor.FinancialInfoExecutor;
import com.tsoftware.qtd.executor.LoanPlanExecutor;
import com.tsoftware.qtd.executor.LoanRequestExecutor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
  CREATE_LOAN_REQUEST(LoanRequestExecutor.class),
  CREATE_LOAN_PLAN(LoanPlanExecutor.class),
  CREATE_FINANCIAL_INFO(FinancialInfoExecutor.class),
  ;
  final Class<?> executor;
}
