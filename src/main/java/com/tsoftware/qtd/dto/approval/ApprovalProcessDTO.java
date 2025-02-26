package com.tsoftware.qtd.dto.approval;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import com.tsoftware.qtd.commonlib.model.AbstractTransaction;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class ApprovalProcessDTO extends AbstractTransaction<ProcessType> {
  private Application application;
  private ApprovalStatus status;
  private List<ApprovalDTO> approvals;
  private List<GroupApprovalDTO> groupApprovals;
  private List<RoleApprovalDTO> roleApprovals;
  private ZonedDateTime approvedAt;
  private List<UUID> referenceIds;

  @Override
  public boolean isApproved() {
    var approved =
        Optional.ofNullable(this.approvals).orElse(new ArrayList<>()).stream()
                .allMatch(approve -> approve.getStatus() == ApprovalStatus.APPROVED)
            && Optional.ofNullable(this.roleApprovals).orElse(new ArrayList<>()).stream()
                .allMatch(RoleApprovalDTO::isApproved)
            && Optional.ofNullable(this.groupApprovals).orElse(new ArrayList<>()).stream()
                .allMatch(GroupApprovalDTO::isApproved);
    var rejected =
        Optional.ofNullable(this.approvals).orElse(new ArrayList<>()).stream()
                .anyMatch(a -> ApprovalStatus.REJECTED.equals(a.getStatus()))
            || Optional.ofNullable(this.groupApprovals).orElse(new ArrayList<>()).stream()
                .anyMatch(g -> ApprovalStatus.REJECTED.equals(g.getStatus()))
            || Optional.ofNullable(this.roleApprovals).orElse(new ArrayList<>()).stream()
                .anyMatch(r -> ApprovalStatus.REJECTED.equals(r.getStatus()));
    this.status =
        approved
            ? ApprovalStatus.APPROVED
            : rejected ? ApprovalStatus.REJECTED : ApprovalStatus.WAIT;
    this.approvedAt = approved ? ZonedDateTime.now() : null;
    return approved;
  }

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @RequiredArgsConstructor
  public static class Application {
    UUID id;
  }
}
