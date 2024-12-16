package com.tsoftware.qtd.dto;

import com.tsoftware.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transaction extends AbstractTransaction {
  // List of whom to approve this transaction
  private List<String> approvers;
  private Integer requiredApprovals;
  private UUID applicationId;
  private ApproveStatus status;

  @Override
  public boolean isApproved() {
    return Optional.ofNullable(approvers)
        .map(approvers -> approvers.size() >= requiredApprovals)
        .orElse(false);
  }
}
