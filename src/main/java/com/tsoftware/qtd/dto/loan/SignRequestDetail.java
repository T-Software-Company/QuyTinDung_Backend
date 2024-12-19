package com.tsoftware.qtd.dto.loan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignRequestDetail {
  LoanInfo loanInfo;
  List<DisbursementDetail> disbursements;
}
