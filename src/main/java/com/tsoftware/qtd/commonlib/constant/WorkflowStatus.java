package com.tsoftware.qtd.commonlib.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WorkflowStatus {
  INPROGRESS("inprogress"),
  DENIED("denied"),
  COMPLETED("completed"),
  EXPIRED("expired");

  final String shortname;

  @JsonValue
  public String getShortName() {
    return shortname;
  }

  WorkflowStatus(String shortname) {
    this.shortname = shortname;
  }

  private static final Map<String, WorkflowStatus> MAP =
      Stream.of(values())
          .collect(Collectors.toMap(WorkflowStatus::getShortName, Function.identity()));

  @Override
  public String toString() {
    return shortname;
  }

  @JsonCreator
  public static WorkflowStatus fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException();
    }
    if (MAP.containsKey(value)) {
      return MAP.get(value);
    } else {
      return valueOf(value);
    }
  }
}
