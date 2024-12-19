package com.tsoftware.qtd.dto.loan;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class LoanInfo {
  Integer termInMonths;
  BigDecimal approvedAmount;
  BigDecimal interestRate;
}
