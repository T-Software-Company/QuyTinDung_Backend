package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.Role;
import jakarta.persistence.*;
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
public class RoleApproveSetting extends AbstractAuditEntity {
  @Enumerated(EnumType.ORDINAL)
  private Role role;

  private Integer requiredCount;
  @ManyToOne private ApproveSetting approveSetting;
}
