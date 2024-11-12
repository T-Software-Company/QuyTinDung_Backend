package com.tsoftware.qtd.dto.loan;

import com.tsoftware.qtd.constants.EnumType.AssetType;
import com.tsoftware.qtd.constants.EnumType.BorrowerType;
import com.tsoftware.qtd.constants.EnumType.LoanCollateralType;
import com.tsoftware.qtd.constants.EnumType.LoanSecurityType;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import com.tsoftware.qtd.entity.LoanPurposeDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class LoanRequestDto {


  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String purpose;
  ZonedDateTime startDate;
  ZonedDateTime endDate;
  BigDecimal amount;
  BorrowerType borrowerType;
  LoanSecurityType loanSecurityType;
  AssetType loanCollateralType;
}
