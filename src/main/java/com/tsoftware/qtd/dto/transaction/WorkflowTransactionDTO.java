package com.tsoftware.qtd.dto.transaction;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import com.tsoftware.qtd.dto.application.ApplicationDTO;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  private ApplicationDTO application;
  private ActionStatus status;
  private List<ApproveDTO> approves;
  private List<GroupApproveDTO> groupApproves;
  private List<RoleApproveDTO> roleApproves;
  private ZonedDateTime approvedAt;
  private UUID referenceId;

  @Override
  public boolean isApproved() {
    var approved =
        Optional.ofNullable(this.approves).orElse(new ArrayList<>()).stream()
                .allMatch(approve -> approve.getStatus() == ActionStatus.APPROVED)
            && Optional.ofNullable(this.roleApproves).orElse(new ArrayList<>()).stream()
                .allMatch(RoleApproveDTO::isApproved)
            && Optional.ofNullable(this.groupApproves).orElse(new ArrayList<>()).stream()
                .allMatch(GroupApproveDTO::isApproved);
    var rejected =
        Optional.ofNullable(this.approves).orElse(new ArrayList<>()).stream()
                .anyMatch(a -> ActionStatus.REJECTED.equals(a.getStatus()))
            || Optional.ofNullable(this.groupApproves).orElse(new ArrayList<>()).stream()
                .anyMatch(g -> ActionStatus.REJECTED.equals(g.getStatus()))
            || Optional.ofNullable(this.roleApproves).orElse(new ArrayList<>()).stream()
                .anyMatch(r -> ActionStatus.REJECTED.equals(r.getStatus()));
    this.status =
        approved ? ActionStatus.APPROVED : rejected ? ActionStatus.REJECTED : ActionStatus.WAIT;
    this.approvedAt = approved ? ZonedDateTime.now() : null;
    return approved;
  }
}
