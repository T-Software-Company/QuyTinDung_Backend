package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.InterestRateType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class InterestRateSettingResponse extends AbstractResponse {
  private InterestRateType type;
  private Integer term;
  private BigDecimal rate;
}
