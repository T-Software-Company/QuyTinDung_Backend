package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ProcessType;
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
public class ApprovalSetting extends AbstractAuditEntity {

  private String name;

  @Column(nullable = false, unique = true)
  private ProcessType processType;

  @OneToMany(
      mappedBy = "approvalSetting",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private List<RoleApprovalSetting> roleApprovalSettings;

  @OneToMany(
      mappedBy = "approvalSetting",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private List<GroupApprovalSetting> groupApprovalSettings;
}
