package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleApprovalDTO {
  private UUID id;
  private Role role;
  private Integer requiredCount;
  private List<ApprovalDTO> currentApprovals;
  private ActionStatus status;
  private ApprovalProcessRequest approvalProcess;

  public boolean isApproved() {
    this.currentApprovals =
        this.currentApprovals != null ? this.currentApprovals : new ArrayList<>();
    var approvedApproves =
        currentApprovals.stream()
            .filter(approve -> approve.getStatus() == ActionStatus.APPROVED)
            .distinct();
    var rejected =
        currentApprovals.stream()
                .filter(a -> ActionStatus.REJECTED.equals(a.getStatus()))
                .distinct()
                .count()
            > this.currentApprovals.size() - this.requiredCount;
    var approved =
        this.currentApprovals.isEmpty() || approvedApproves.count() >= this.requiredCount;
    this.status =
        approved ? ActionStatus.APPROVED : rejected ? ActionStatus.REJECTED : ActionStatus.WAIT;
    return approved;
  }
}
