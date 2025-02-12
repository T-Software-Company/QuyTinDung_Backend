package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class LoanRequestResponse extends AbstractResponse {
  private String purpose;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private BigDecimal amount;
  private BorrowerType borrowerType;
  private LoanSecurityType loanSecurityType;
  private List<AssetType> loanCollateralTypes;
  private String note;
  private Map<String, Object> metadata;
}
