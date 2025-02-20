package com.tsoftware.qtd.event;

import java.util.UUID;

public class AssetSubmittedEvent extends SubmittedEvent {
  public AssetSubmittedEvent(Object object, UUID id) {
    super(object, id);
  }
}
