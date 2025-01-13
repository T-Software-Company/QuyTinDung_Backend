package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import java.util.List;
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

  @Override
  public boolean isApproved() {
    return approves != null
        && approves.stream()
                .filter(approve -> ApproveStatus.APPROVED.equals(approve.getStatus()))
                .count()
            >= requiredApprovals;
  }
}
