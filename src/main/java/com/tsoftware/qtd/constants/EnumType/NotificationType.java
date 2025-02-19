package com.tsoftware.qtd.constants.EnumType;

import com.tsoftware.qtd.handler.NotificationHandler;
import com.tsoftware.qtd.handler.SubmittedHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  CREATE_LOAN_REQUEST(SubmittedHandler.class),
  CREATE_LOAN_PLAN(SubmittedHandler.class);
  final Class<? extends NotificationHandler> handler;
}
