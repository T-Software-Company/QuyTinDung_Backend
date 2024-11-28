package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoanRequestRequest {
  @NotNull @NotBlank private String purpose;
  @NotNull @NotBlank private ZonedDateTime startDate;

  @NotNull @NotBlank private ZonedDateTime endDate;

  @NotNull @NotBlank private BigDecimal amount;

  @NotNull
  @IsEnum(enumClass = BorrowerType.class)
  private String borrowerType;

  @NotNull
  @IsEnum(enumClass = LoanSecurityType.class)
  private String loanSecurityType;

  @IsEnum(enumClass = AssetType.class)
  private String loanCollateralType;

  private String note;
  private Map<String, Object> metadata;
}
