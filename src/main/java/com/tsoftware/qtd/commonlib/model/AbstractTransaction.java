package com.tsoftware.qtd.commonlib.model;

import com.tsoftware.qtd.commonlib.constant.ApproveStatus;
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
  private String createdBy;
  private T type;
  private Object metadata;
  private ApproveStatus status;

  public abstract boolean isApproved();
}
