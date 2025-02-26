package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.application.CreditResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreditRatingCreatedEvent extends ApplicationEvent {

  private final CreditResponse creditResponse;

  public CreditRatingCreatedEvent(Object source, CreditResponse creditResponse) {
    super(source);
    this.creditResponse = creditResponse;
  }
}
