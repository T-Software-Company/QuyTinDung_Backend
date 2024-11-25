package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import com.tsoftware.qtd.model.StepHistory;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "onboarding_workflow")
public class OnboardingWorkflowEntity extends AbstractAuditEntity {
  private UUID targetUuid;
  private String currentStep;
  private String nextStep;
  private WorkflowStatus status;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  private List<StepHistory> stepHistories;
}
