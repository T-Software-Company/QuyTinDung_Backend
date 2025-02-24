package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ApprovalStatus;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
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
public class GroupApproval extends AbstractAuditEntity {
  private UUID groupId;
  private Integer requiredPercentage;

  @OneToMany(
      mappedBy = "groupApproval",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Approval> currentApprovals;

  private ApprovalStatus status;

  @ManyToOne private ApprovalProcess approvalProcess;
}
