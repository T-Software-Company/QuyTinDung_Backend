package com.tsoftware.qtd.constants.EnumType;

import com.tsoftware.qtd.handler.LoanRequestSubmittedHandler;
import com.tsoftware.qtd.handler.NotificationHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  CREATE_LOAN_REQUEST(LoanRequestSubmittedHandler.class);
  final Class<? extends NotificationHandler> handler;
}
