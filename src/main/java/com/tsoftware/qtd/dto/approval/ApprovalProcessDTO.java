package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
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
public class ApprovalProcessDTO extends AbstractTransaction<ProcessType> {
  private ApplicationDTO application;
  private ActionStatus status;
  private List<ApprovalDTO> approvals;
  private List<GroupApprovalDTO> groupApprovals;
  private List<RoleApprovalDTO> roleApprovals;
  private ZonedDateTime approvedAt;
  private List<UUID> referenceIds;

  @Override
  public boolean isApproved() {
    var approved =
        Optional.ofNullable(this.approvals).orElse(new ArrayList<>()).stream()
                .allMatch(approve -> approve.getStatus() == ActionStatus.APPROVED)
            && Optional.ofNullable(this.roleApprovals).orElse(new ArrayList<>()).stream()
                .allMatch(RoleApprovalDTO::isApproved)
            && Optional.ofNullable(this.groupApprovals).orElse(new ArrayList<>()).stream()
                .allMatch(GroupApprovalDTO::isApproved);
    var rejected =
        Optional.ofNullable(this.approvals).orElse(new ArrayList<>()).stream()
                .anyMatch(a -> ActionStatus.REJECTED.equals(a.getStatus()))
            || Optional.ofNullable(this.groupApprovals).orElse(new ArrayList<>()).stream()
                .anyMatch(g -> ActionStatus.REJECTED.equals(g.getStatus()))
            || Optional.ofNullable(this.roleApprovals).orElse(new ArrayList<>()).stream()
                .anyMatch(r -> ActionStatus.REJECTED.equals(r.getStatus()));
    this.status =
        approved ? ActionStatus.APPROVED : rejected ? ActionStatus.REJECTED : ActionStatus.WAIT;
    this.approvedAt = approved ? ZonedDateTime.now() : null;
    return approved;
  }
}
