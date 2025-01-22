package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class Approval extends AbstractAuditEntity {
  private String comment;
  @ManyToOne private Employee approver;

  @Enumerated(EnumType.ORDINAL)
  private ActionStatus status;

  @ManyToOne private ApprovalProcess approvalProcess;
  @ManyToOne private GroupApproval groupApproval;
  @ManyToOne private RoleApproval roleApproval;
}
