package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.util.Map;
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
public class AppraisalReport extends AbstractAuditEntity {

  private String title;
  private String note;

  @OneToOne(mappedBy = "appraisalReport", fetch = FetchType.LAZY)
  private AppraisalPlan appraisalPlan;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;
}
