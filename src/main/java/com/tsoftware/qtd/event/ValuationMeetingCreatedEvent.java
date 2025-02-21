package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.Valuation.ValuationMeetingResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ValuationMeetingCreatedEvent extends ApplicationEvent {
  private final ValuationMeetingResponse valuationMeetingResponse;

  public ValuationMeetingCreatedEvent(
      Object source, ValuationMeetingResponse valuationMeetingResponse) {
    super(source);
    this.valuationMeetingResponse = valuationMeetingResponse;
  }
}
