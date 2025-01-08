package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import com.tsoftware.qtd.commonlib.model.StepHistory;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table
@EqualsAndHashCode(callSuper = true)
public class OnboardingWorkflowEntity extends AbstractAuditEntity {
  private UUID targetId;
  private String PIC;
  private List<String> nextSteps;
  private WorkflowStatus workflowStatus;
  private WorkflowStep workflowStep;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<StepHistory> stepHistories;
}
