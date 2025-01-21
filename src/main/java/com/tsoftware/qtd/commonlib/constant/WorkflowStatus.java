package com.tsoftware.qtd.commonlib.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WorkflowStatus {
  INPROGRESS("inprogress"),
  DENIED("denied"),
  COMPLETED("completed"),
  CANCELLED("cancelled");

  final String shortname;

  @Override
  public String toString() {
    return shortname;
  }
}
