package com.tsoftware.qtd.dto.credit;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class IncomeProofDto {

  Long id;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  String lastModifiedBy;
  String createdBy;
  String link;
  IncomeProofType incomeProofType;
}
