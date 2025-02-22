package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ActionStatus;
import com.tsoftware.qtd.constants.EnumType.ProcessType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Data
@Table
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApprovalProcess extends AbstractAuditEntity {

  @Enumerated(EnumType.ORDINAL)
  private ActionStatus status;

  @Enumerated(EnumType.ORDINAL)
  private ProcessType type;

  private List<UUID> referenceIds;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime approvedAt;

  @OneToMany(
      mappedBy = "approvalProcess",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Approval> approvals;

  @OneToMany(
      mappedBy = "approvalProcess",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<GroupApproval> groupApprovals;

  @OneToMany(
      mappedBy = "approvalProcess",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<RoleApproval> roleApprovals;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Object metadata;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;
}
