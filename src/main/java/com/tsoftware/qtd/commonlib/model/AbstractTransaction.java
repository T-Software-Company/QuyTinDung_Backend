package com.tsoftware.qtd.commonlib.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTransaction<T extends Enum<?>> {
  private UUID id;
  private String PIC;
  private T type;
  private Object metadata;

  public abstract boolean isApproved();
}
