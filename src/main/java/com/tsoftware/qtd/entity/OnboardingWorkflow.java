package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.commonlib.constant.WorkflowStatus;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
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
public class OnboardingWorkflow extends AbstractAuditEntity {
  private UUID targetId;
  private List<String> nextSteps;
  private List<String> currentSteps;

  @Enumerated(EnumType.ORDINAL)
  private WorkflowStatus status;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime startTime;

  @Column(columnDefinition = "TIME WITH TIME ZONE")
  private ZonedDateTime endTime;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Column
  @OneToMany(mappedBy = "onboardingWorkflow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<StepHistory> stepHistories;
}
