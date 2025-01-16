package com.tsoftware.qtd.entity;

import jakarta.persistence.*;
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
public class GroupApproveSetting extends AbstractAuditEntity {
  private UUID groupId;
  private Integer requiredPercentage;
  @ManyToOne private ApproveSetting approveSetting;
}
