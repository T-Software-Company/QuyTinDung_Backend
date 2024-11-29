package com.tsoftware.qtd.exception;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

public class CommonException extends RuntimeException {

  @Serial private static final long serialVersionUID = -2129592878036824132L;
  @Getter private final CommonError response;
  private final transient List<Object> parameters = new ArrayList<>();

  public CommonException(CommonError response) {
    this(response, null, new Object[0]);
  }

  public CommonException(CommonError response, Object... params) {
    this(response, null, params);
  }

  public CommonException(CommonError response, Throwable cause, Object... params) {
    super(response.getMessage(), cause);
    this.response = response;
    Collections.addAll(this.parameters, params);
  }

  public List<Object> getParameters() {
    return Collections.unmodifiableList(parameters);
  }

  public void addParameters(Object param) {
    this.parameters.add(param);
  }
}
