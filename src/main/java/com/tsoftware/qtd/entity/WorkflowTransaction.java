package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
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
public class WorkflowTransaction extends AbstractAuditEntity {

  @Enumerated(EnumType.ORDINAL)
  private ApproveStatus status;

  @Enumerated(EnumType.ORDINAL)
  private TransactionType type;

  private UUID referenceId;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime approvedAt;

  private String PIC;

  @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Approve> approves;

  @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<GroupApprove> groupApproves;

  @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<RoleApprove> roleApproves;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Object metadata;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;
}
