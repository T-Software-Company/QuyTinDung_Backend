package com.tsoftware.qtd.commonlib.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTransaction {
  private UUID id;
  private String PIC;
  private String type;
  private UUID customerId;
  private Object metadata;

  public abstract boolean isApproved();
}
