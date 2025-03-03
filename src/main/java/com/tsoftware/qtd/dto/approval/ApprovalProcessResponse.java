package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import com.tsoftware.qtd.dto.AbstractResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalProcessResponse extends AbstractResponse {
  private ApprovalStatus status;
  private ProcessType type;
  private List<UUID> referenceIds;
  private ZonedDateTime approvedAt;
  private Object metadata;
  private Application application;

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Application {
    String id;
  }
}
