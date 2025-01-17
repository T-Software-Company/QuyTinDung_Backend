package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
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
public class WorkflowTransactionResponse extends AbstractResponse {
  private ApproveStatus status;
  private TransactionType type;
  private UUID referenceId;
  private ZonedDateTime approvedAt;
  private List<ApproveResponse> approves;
  private List<GroupApproveResponse> groupApproves;
  private List<RoleApproveResponse> roleApproves;
  private Object metadata;
  private Application application;

  @Builder
  @Getter
  @Setter
  public static class Application {
    String id;
  }
}
