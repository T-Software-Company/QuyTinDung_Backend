package com.tsoftware.qtd.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder
public class WorkflowStep extends AbstractAuditEntity {
  private String version;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<Object> steps;
}
