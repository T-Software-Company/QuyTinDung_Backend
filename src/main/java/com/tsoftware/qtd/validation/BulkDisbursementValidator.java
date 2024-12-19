package com.tsoftware.qtd.validation;

import com.tsoftware.qtd.dto.loan.DisbursementDetail;
import com.tsoftware.qtd.entity.LoanAccount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.stereotype.Component;

@Component
public class BulkDisbursementValidator {
  public void validate(LoanAccount loanAccount, List<DisbursementDetail> disbursements) {
    // Calculate total requested disbursement
    BigDecimal totalRequestedAmount =
        disbursements.stream()
            .map(DisbursementDetail::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Validate against remaining available amount
    BigDecimal remainingAvailable =
        loanAccount.getApprovedAmount().subtract(loanAccount.getDisbursedAmount());

    if (totalRequestedAmount.compareTo(remainingAvailable) > 0) {
      throw new InvalidOperationException(
          String.format(
              "Total disbursement amount (%s) exceeds remaining available amount (%s)",
              totalRequestedAmount, remainingAvailable));
    }

    // Validate disbursement dates are in chronological order
    List<ZonedDateTime> dates =
        disbursements.stream()
            .map(DisbursementDetail::getDisbursementDate)
            .sorted()
            .collect(Collectors.toList());

    if (!isSorted(dates)) {
      throw new InvalidOperationException("Disbursement dates must be in chronological order");
    }
  }

  private boolean isSorted(List<ZonedDateTime> dates) {
    for (int i = 0; i < dates.size() - 1; i++) {
      if (dates.get(i).isAfter(dates.get(i + 1))) {
        return false;
      }
    }
    return true;
  }
}
