package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class WorkflowTransactionDTO extends AbstractTransaction<TransactionType> {
  private Integer requiredApprovals;
  private ApplicationDTO application;
  private ApproveStatus status;
  private List<ApproveDTO> approves;
  private ZonedDateTime approvedAt;
  private UUID referenceId;

  @Override
  public boolean isApproved() {
    var result =
        approves != null
            && approves.stream()
                    .filter(approve -> ApproveStatus.APPROVED.equals(approve.getStatus()))
                    .count()
                >= requiredApprovals;
    this.status = result ? ApproveStatus.APPROVED : ApproveStatus.REJECTED;
    this.approvedAt = result ? ZonedDateTime.now() : null;
    return result;
  }
}
