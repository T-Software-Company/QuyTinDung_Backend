package com.tsoftware.qtd.commonlib.model;

import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractTransaction<T extends Enum<?>> {
  private UUID id;
  private String PIC;
  private T type;
  private Object metadata;

  public abstract boolean isApproved();
}
