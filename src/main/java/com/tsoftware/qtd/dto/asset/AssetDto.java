package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.entity.AppraisalReport;
import com.tsoftware.qtd.entity.Credit;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssetDto {

  @NotNull(message = "ASSET_ID_REQUIRED")
  Long assetId;

  @NotNull(message = "APPRAISAL_REPORT_REQUIRED")
  @Valid
  AppraisalReport appraisalReport;

  //  @NotNull(message = "ASSET_TYPE_REQUIRED")
  //  AssetType assetType;

  @NotNull(message = "LOAN_REQUIRED")
  Credit credit;
}
