package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ApprovalResponse {
  private UUID id;
  private String comment;
  private ProcessType processType;
  private ApprovalStatus status;
  private Approver approver;
  private Boolean canApprove;
  private ApprovalProcessResponse approvalProcess;
  private GroupApprovalResponse groupApproval;
  private RoleApprovalResponse roleApproval;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Approver {
    UUID id;
  }
}
