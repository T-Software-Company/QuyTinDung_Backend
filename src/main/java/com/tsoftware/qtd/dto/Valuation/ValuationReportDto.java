package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.dto.ApproveDto;
import com.tsoftware.qtd.dto.asset.AssetDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
public class ValuationReportDto {
  private BigDecimal totalValuationAmount;
  private List<AssetDto> assets;
  private List<ApproveDto> approves;
}
