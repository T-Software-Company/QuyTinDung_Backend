package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.Role;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
public class RoleApproval extends AbstractAuditEntity {
  @Enumerated(EnumType.ORDINAL)
  private Role role;

  private Integer requiredCount;

  @OneToMany(
      mappedBy = "roleApproval",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Approval> currentApprovals;

  private ActionStatus status;

  @ManyToOne private ApprovalProcess approvalProcess;
}
