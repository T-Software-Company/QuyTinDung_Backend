package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.TransactionType;
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
public class ApproveSetting extends AbstractAuditEntity {

  private String name;

  @Column(nullable = false, unique = true)
  private TransactionType transactionType;

  @OneToMany(mappedBy = "approveSetting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<RoleApproveSetting> roleApproveSettings;

  @OneToMany(mappedBy = "approveSetting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<GroupApproveSetting> groupApproveSettings;
}
