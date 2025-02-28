package com.tsoftware.qtd.dto.appraisal;

import com.tsoftware.qtd.dto.application.ApplicationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppraisalReportRequest {
  @NotBlank(message = "Title cannot be blank")
  private String title;

  private String note;

  private Map<String, Object> metadata;

  @Valid @NotNull private ApplicationRequest application;
}
