package com.tsoftware.qtd.dto.appraisal;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.util.Map;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class AppraisalReportResponse extends AbstractResponse {
  private String title;
  private String note;
  private Map<String, Object> metadata;
  private Application application;

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  public static class Application {
    UUID id;
  }
}
