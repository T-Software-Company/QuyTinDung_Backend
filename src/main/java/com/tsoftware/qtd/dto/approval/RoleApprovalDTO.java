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
  private List<ApprovalDTO> currentApproves;
  private ActionStatus status;
  private ApprovalProcessRequest approvalProcess;

  public boolean isApproved() {
    this.currentApproves = this.currentApproves != null ? this.currentApproves : new ArrayList<>();
    var approvedApproves =
        currentApproves.stream()
            .filter(approve -> approve.getStatus() == ActionStatus.APPROVED)
            .distinct();
    var rejected =
        currentApproves.stream()
                .filter(a -> ActionStatus.REJECTED.equals(a.getStatus()))
                .distinct()
                .count()
            > this.currentApproves.size() - this.requiredCount;
    var approved = this.currentApproves.isEmpty() || approvedApproves.count() >= this.requiredCount;
    this.status =
        approved ? ActionStatus.APPROVED : rejected ? ActionStatus.REJECTED : ActionStatus.WAIT;
    return approved;
  }
}
