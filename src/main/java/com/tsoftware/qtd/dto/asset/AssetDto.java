package com.tsoftware.qtd.dto.asset;

import com.tsoftware.qtd.entity.AppraisalReport;
import com.tsoftware.qtd.entity.AssetType;
import com.tsoftware.qtd.entity.Loan;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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

  @NotNull(message = "ASSET_TYPE_REQUIRED")
  @Size(min = 1, message = "ASSET_TYPE_LIST_EMPTY")
  List<AssetType> assetType;

  @NotNull(message = "LOAN_REQUIRED")
  Loan loan;
}
