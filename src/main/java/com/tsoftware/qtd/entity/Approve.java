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
public class Approve extends AbstractAuditEntity {
  private String comment;
  @ManyToOne private Employee approver;

  @Enumerated(EnumType.ORDINAL)
  private ActionStatus status;

  @ManyToOne private WorkflowTransaction transaction;
  @ManyToOne private GroupApprove groupApprove;
  @ManyToOne private RoleApprove roleApprove;
}
