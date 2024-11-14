package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.dto.ApproveResponse;
import com.tsoftware.qtd.dto.asset.AssetRequest;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValuationReportResponse {
  private Long id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private BigDecimal totalValuationAmount;
  private List<AssetRequest> assets;
  private List<ApproveResponse> approves;
}
