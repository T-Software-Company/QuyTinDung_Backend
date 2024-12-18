package com.tsoftware.qtd.constants.EnumType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
  CREATE_LOAN_REQUEST("loanRequestExecutor"),
  CREATE_LOAN_PLAN("loanPlanExecutor");
  final String executor;
  public static final Map<String, String> executorMap =
      Arrays.stream(TransactionType.values())
          .collect(Collectors.toMap(TransactionType::name, TransactionType::getExecutor));
}
