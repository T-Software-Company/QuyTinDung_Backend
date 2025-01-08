package com.tsoftware.qtd.dto;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public abstract class AbstractResponse {
  private UUID id;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String lastModifiedBy;
  private String createdBy;
  private boolean isDeleted;
}
