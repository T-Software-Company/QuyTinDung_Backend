package com.tsoftware.qtd.dto.Valuation;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
public class ValuationReportRequest {
  private BigDecimal totalValuationAmount;
}
