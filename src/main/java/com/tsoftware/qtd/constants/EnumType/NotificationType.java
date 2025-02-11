package com.tsoftware.qtd.constants.EnumType;

import com.tsoftware.qtd.handler.CreateLoanRequestNotificationHandler;
import com.tsoftware.qtd.handler.NotificationHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  CREATE_LOAN_REQUEST(CreateLoanRequestNotificationHandler.class);
  final Class<? extends NotificationHandler> handle;
}
