package com.tsoftware.qtd.entity;

import com.tsoftware.qtd.constants.EnumType.DocumentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "document")
public class Document extends AbstractAuditEntity {
  private UUID customerId;
  private String url;
  private String title;
  private Boolean isUsed;

  @Enumerated(EnumType.STRING)
  private DocumentType type;
}
