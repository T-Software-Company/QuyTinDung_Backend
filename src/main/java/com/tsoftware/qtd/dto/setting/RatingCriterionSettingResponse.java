package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.math.BigDecimal;
import java.util.Map;
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
public class RatingCriterionSettingResponse extends AbstractResponse {
  private RatingCriterionType ratingCriterionType;
  private Integer weigh;
  private Integer coefficient;
  private String title;
  private Map<String, BigDecimal> scoreMapping;
}
