package com.tsoftware.qtd.commonlib.util;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestExtractor {
  public Object extract(JoinPoint joinPoint) {
    Map<String, Object> argsMap = extractMethodArguments(joinPoint);
    enrichWithHttpRequest(argsMap);
    enrichWithUserContext(argsMap);
    return argsMap;
  }

  private Map<String, Object> extractMethodArguments(JoinPoint joinPoint) {
    // ...extract method arguments logic...
  }

  private void enrichWithHttpRequest(Map<String, Object> argsMap) {
    // ...HTTP request enrichment logic...
  }

  private void enrichWithUserContext(Map<String, Object> argsMap) {
    // ...user context enrichment logic...
  }
}
