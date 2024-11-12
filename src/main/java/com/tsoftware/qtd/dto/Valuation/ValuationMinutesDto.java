package com.tsoftware.qtd.dto.Valuation;

import com.tsoftware.qtd.entity.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
public class ValuationMinutesDto {
  private BigDecimal totalValuationAmount;
}
