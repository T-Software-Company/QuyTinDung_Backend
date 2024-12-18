package com.tsoftware.qtd.entity;

import com.tsoftware.commonlib.constant.WorkflowStatus;
import com.tsoftware.commonlib.model.StepHistory;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public class OnboardingWorkflowEntity extends AbstractAuditEntity {
  private UUID targetId;
  private String currentStep;
  private String nextStep;
  private WorkflowStatus workflowStatus;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<StepHistory> stepHistories;
}
