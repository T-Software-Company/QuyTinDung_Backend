package com.tsoftware.qtd.event;

import com.tsoftware.qtd.dto.appraisal.AppraisalMeetingResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AppraisalMeetingCreatedEvent extends ApplicationEvent {
  private final AppraisalMeetingResponse appraisalMeetingResponse;

  public AppraisalMeetingCreatedEvent(
      Object source, AppraisalMeetingResponse appraisalMeetingResponse) {
    super(source);
    this.appraisalMeetingResponse = appraisalMeetingResponse;
  }
}
