package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
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
  private ApprovalStatus status;
  private ApprovalProcessRequest approvalProcess;

  public boolean isApproved() {
    this.currentApprovals =
        this.currentApprovals != null ? this.currentApprovals : new ArrayList<>();
    var approvedApproves =
        currentApprovals.stream()
            .filter(approve -> approve.getStatus() == ApprovalStatus.APPROVED)
            .distinct();
    var rejected =
        currentApprovals.stream()
                .filter(a -> ApprovalStatus.REJECTED.equals(a.getStatus()))
                .distinct()
                .count()
            > this.currentApprovals.size() - this.requiredCount;
    var approved =
        this.currentApprovals.isEmpty() || approvedApproves.count() >= this.requiredCount;
    this.status =
        approved
            ? ApprovalStatus.APPROVED
            : rejected ? ApprovalStatus.REJECTED : ApprovalStatus.WAIT;
    return approved;
  }
}
