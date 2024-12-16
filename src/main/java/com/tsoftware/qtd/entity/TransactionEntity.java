package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.ApproveStatus;
import com.tsoftware.qtd.constants.EnumType.TransactionType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class TransactionEntity extends AbstractAuditEntity {
  private UUID customerId;

  @Enumerated(EnumType.ORDINAL)
  private ApproveStatus status;

  @Enumerated(EnumType.ORDINAL)
  private TransactionType type;

  private ZonedDateTime approvedAt;
  private String approvedBy;
  private Integer requiredApprovals;
  private String PIC;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<String> approvers;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Object metadata;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Application application;
}
