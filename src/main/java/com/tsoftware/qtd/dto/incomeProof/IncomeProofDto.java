package com.tsoftware.qtd.dto.incomeProof;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import com.tsoftware.qtd.dto.appraisalPlan.AppraisalPlanDto;
import com.tsoftware.qtd.dto.customer.CustomerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class IncomeProofDto {


  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String link;
  IncomeProofType incomeProofType;
}
