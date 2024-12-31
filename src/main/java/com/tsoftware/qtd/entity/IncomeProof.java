package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.IncomeProofType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.util.Map;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class IncomeProof extends AbstractAuditEntity {

  private String link;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;

  @Enumerated(EnumType.ORDINAL)
  private IncomeProofType incomeProofType;
}
