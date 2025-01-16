package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
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
public class GroupApprove extends AbstractAuditEntity {
  private UUID groupId;
  private Integer requiredPercentage;

  @OneToMany(mappedBy = "groupApprove")
  private List<Approve> currentApproves;

  private ApproveStatus status;

  @ManyToOne private WorkflowTransaction transaction;
}
