package com.tsoftware.qtd.dto.setting;

import com.tsoftware.qtd.constants.EnumType.RatingCriterionType;
import com.tsoftware.qtd.validation.IsEnum;
import jakarta.validation.constraints.*;
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
public class RatingCriterionSettingRequest {

  @NotNull(message = "Rating criterion type is required")
  @IsEnum(enumClass = RatingCriterionType.class)
  private String ratingCriterionType;

  @NotNull(message = "Weigh is required")
  @Min(value = 0, message = "Weigh must be positive")
  private Integer weigh;

  @NotNull(message = "Coefficient is required")
  @Min(value = 0, message = "Coefficient must be positive")
  private Integer coefficient;

  @NotBlank(message = "Title is required")
  @Size(max = 255, message = "Title must not exceed 255 characters")
  private String title;

  @NotNull(message = "Score mapping is required")
  private Map<String, BigDecimal> scoreMapping;
}
