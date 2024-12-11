package com.tsoftware.qtd.dto;

import com.tsoftware.commonlib.model.AbstractTransaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction extends AbstractTransaction {
  // List of whom to approve this transaction
  private List<String> approvers;
  private UUID applicationId;
  private Boolean multipleApproval;
  private Integer requiredApprovals;

  @Override
  public boolean isApproved() {
    return Optional.ofNullable(approvers)
        .map(approvers -> approvers.size() >= requiredApprovals)
        .orElse(false);
  }
}
