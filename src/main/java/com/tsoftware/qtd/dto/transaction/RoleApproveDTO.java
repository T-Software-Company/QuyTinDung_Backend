package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleApproveDTO {
  private Role role;
  private Integer requiredCount;
  private List<ApproveDTO> currentApproves;
  private ApproveStatus status;
  private WorkflowTransactionRequest transaction;

  public boolean isApproved() {
    var approvedApproves =
        currentApproves.stream()
            .filter(approve -> approve.getStatus() == ApproveStatus.APPROVED)
            .distinct();
    var result = this.currentApproves.isEmpty() || approvedApproves.count() >= this.requiredCount;
    this.status = result ? ApproveStatus.APPROVED : ApproveStatus.REJECTED;
    return result;
  }
}
