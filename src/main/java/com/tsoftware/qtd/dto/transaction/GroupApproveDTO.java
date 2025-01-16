package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupApproveDTO {
  private UUID groupId;
  private Integer requiredPercentage;
  private List<ApproveDTO> currentApproves;
  private ApproveStatus status;
  private WorkflowTransactionRequest transaction;

  public boolean isApproved() {
    var approvedApproves =
        currentApproves.stream()
            .filter(approve -> approve.getStatus() == ApproveStatus.APPROVED)
            .distinct();
    var result =
        Optional.ofNullable(this.currentApproves).orElse(new ArrayList<>()).isEmpty()
            || approvedApproves.count() * 100 / this.currentApproves.size() >= requiredPercentage;
    this.status = result ? ApproveStatus.APPROVED : ApproveStatus.REJECTED;
    return result;
  }
}
