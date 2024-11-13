package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoanRequestRequest {
  String purpose;
  ZonedDateTime startDate;
  ZonedDateTime endDate;
  BigDecimal amount;
  BorrowerType borrowerType;
  LoanSecurityType loanSecurityType;
  AssetType loanCollateralType;
  String note;
}
