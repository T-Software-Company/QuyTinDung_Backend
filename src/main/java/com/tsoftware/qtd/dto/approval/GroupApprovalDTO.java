package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupApprovalDTO {
  private UUID id;
  private UUID groupId;
  private Integer requiredPercentage;
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
    var approved = true;
    var rejected = false;
    if (!this.currentApprovals.isEmpty()) {
      approved =
          approvedApproves.count() * 100 / this.currentApprovals.size() >= this.requiredPercentage;
      rejected =
          100
                  - this.currentApprovals.stream()
                          .filter(a -> ApprovalStatus.REJECTED.equals(a.getStatus()))
                          .count()
                      * 100
                      / this.currentApprovals.size()
              < this.requiredPercentage;
    }
    this.status =
        approved
            ? ApprovalStatus.APPROVED
            : rejected ? ApprovalStatus.REJECTED : ApprovalStatus.WAIT;
    return approved;
  }
}
