package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.application.CreditRatingResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreditRatingCreatedEvent extends ApplicationEvent {

  private final CreditRatingResponse creditRatingResponse;

  public CreditRatingCreatedEvent(Object source, CreditRatingResponse creditRatingResponse) {
    super(source);
    this.creditRatingResponse = creditRatingResponse;
  }
}
