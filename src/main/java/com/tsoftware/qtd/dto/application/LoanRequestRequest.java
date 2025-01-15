package com.tsoftware.qtd.dto.application;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
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

  @NotNull @NotBlank private BigDecimal amount;

  @NotNull
  @IsEnum(enumClass = BorrowerType.class)
  private String borrowerType;

  private String asset;

  @NotNull
  @IsEnum(enumClass = LoanSecurityType.class)
  private String loanSecurityType;

  @IsEnum(enumClass = AssetType.class)
  private String loanCollateralType;

  private String note;
  private Map<String, Object> metadata;
  private Set<String> assignees;
  @Valid private ApplicationRequest application;
}
