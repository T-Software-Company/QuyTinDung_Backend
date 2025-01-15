package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.StepType;
import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class StepHistory extends AbstractAuditEntity {
  private String name;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime startTime;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime endTime;

  private List<String> nextSteps;

  @Enumerated(EnumType.ORDINAL)
  private StepType type;

  @Enumerated(EnumType.ORDINAL)
  private WorkflowStatus status;

  private UUID transactionId;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @JoinColumn(nullable = false)
  @ManyToOne
  private OnboardingWorkflow onboardingWorkflow;
}
