package com.tsoftware.qtd.dto.loan;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignResponseDetail {
  UUID loanId;
  String accountNumber;
}
