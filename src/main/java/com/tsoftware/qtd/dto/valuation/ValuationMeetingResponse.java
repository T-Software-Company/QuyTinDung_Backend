package com.tsoftware.qtd.dto.valuation;

import com.tsoftware.qtd.dto.AbstractResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
public class ValuationMeetingResponse extends AbstractResponse {

  private String address;
  private String note;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private List<Employee> participants;
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

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  public static class Employee {
    UUID id;
  }
}
