package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
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
  private List<ApprovalDTO> currentApproves;
  private ActionStatus status;
  private ApprovalProcessRequest approvalProcess;

  public boolean isApproved() {
    this.currentApproves = this.currentApproves != null ? this.currentApproves : new ArrayList<>();
    var approvedApproves =
        currentApproves.stream()
            .filter(approve -> approve.getStatus() == ActionStatus.APPROVED)
            .distinct();
    var approved = true;
    var rejected = false;
    if (!this.currentApproves.isEmpty()) {
      approved =
          approvedApproves.count() * 100 / this.currentApproves.size() >= this.requiredPercentage;
      rejected =
          100
                  - this.currentApproves.stream()
                          .filter(a -> ActionStatus.REJECTED.equals(a.getStatus()))
                          .count()
                      * 100
                      / this.currentApproves.size()
              < this.requiredPercentage;
    }
    this.status =
        approved ? ActionStatus.APPROVED : rejected ? ActionStatus.REJECTED : ActionStatus.WAIT;
    return approved;
  }
}
