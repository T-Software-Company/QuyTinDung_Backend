package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.application.ApplicationResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CancelledEvent extends ApplicationEvent {
  private final ApplicationResponse applicationResponse;

  public CancelledEvent(Object object, ApplicationResponse response) {
    super(object);
    this.applicationResponse = response;
  }
}
