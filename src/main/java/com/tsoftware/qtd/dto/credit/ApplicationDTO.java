package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.ApplicationStep;
import com.tsoftware.qtd.dto.Transaction;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

  private BigDecimal amount;
  private ApplicationStep step;
  List<Transaction> transactions;
  private ZonedDateTime startDate;
  private ZonedDateTime dueDate;
  private BigDecimal interestRate;
  private BigDecimal amountPaid;
  private BigDecimal currentOutstandingDebt;
}
