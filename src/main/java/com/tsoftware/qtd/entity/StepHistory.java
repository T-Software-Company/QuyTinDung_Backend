package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder
public class StepHistory extends AbstractAuditEntity {
  private String name;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime startTime;

  @Column(columnDefinition = "TIME WITH TIMEZONE")
  private ZonedDateTime endTime;

  private List<String> nextSteps;
  private WorkflowStatus status;
  private String transactionId;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToOne private OnboardingWorkflow onboardingWorkflow;
}
