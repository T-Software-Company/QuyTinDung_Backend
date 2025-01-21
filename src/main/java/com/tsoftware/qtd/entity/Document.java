package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.DocumentType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table
public class Document extends AbstractAuditEntity {
  private UUID customerId;
  private String url;
  private String title;
  private Boolean isUsed;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> metadata;

  @Enumerated(EnumType.ORDINAL)
  private DocumentType type;
}
