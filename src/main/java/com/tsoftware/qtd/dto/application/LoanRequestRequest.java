package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class LoanRequestRequest {
  @NotNull @NotBlank private String purpose;

  @NotNull private BigDecimal amount;

  @NotNull
  @IsEnum(enumClass = BorrowerType.class)
  private String borrowerType;

  private String asset;

  @NotNull
  @IsEnum(enumClass = LoanSecurityType.class)
  private String loanSecurityType;

  @NotNull
  @IsEnum(enumClass = AssetType.class)
  private List<String> loanCollateralTypes;

  private String note;
  private Map<String, Object> metadata;
  @NotNull @Valid private ApplicationRequest application;
}
