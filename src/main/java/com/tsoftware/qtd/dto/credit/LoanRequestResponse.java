package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoanRequestResponse {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;

  private String purpose;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private BigDecimal amount;
  private BorrowerType borrowerType;
  private LoanSecurityType loanSecurityType;
  private AssetType loanCollateralType;
  private String note;
  private Map<String, Object> metadata;
}
