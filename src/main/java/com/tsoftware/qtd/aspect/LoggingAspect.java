package com.tsoftware.qtd.aspect;

import com.tsoftware.qtd.util.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Order(1)
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @AfterReturning(
      value = "execution(* com.tsoftware.qtd.exception.GlobalExceptionHandler..*(..))",
      returning = "objResponse")
  public void afterExceptionAdvise(JoinPoint joinPoint, Object objResponse) {
    if (objResponse instanceof ResponseEntity<?> response) {
      logResponse(getRequest(), response.getBody());
    }
  }

  @Around(value = "execution(* com.tsoftware.qtd.controller..*(..))")
  public Object proceedLogForRequest(ProceedingJoinPoint pjp) throws Throwable {
    var request = getRequest();
    var args = pjp.getArgs();
    logRequest(request, args);
    var response = pjp.proceed();
    logResponse(getRequest(), response);

    return response;
  }

  private HttpServletRequest getRequest() {
    return ((ServletRequestAttributes)
            Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
        .getRequest();
  }

  public void logRequest(HttpServletRequest request, Object args) {
    try {
      String jReq = JsonParser.toString(args);
      log.info("Request {}, args: {}", request.getRequestURI(), jReq);
    } catch (Exception ignore) {
      // ignore exception
    }
  }

  public void logResponse(HttpServletRequest request, Object body) {
    try {
      String jReq = JsonParser.toString(body);
      log.info("Response {}, body: {}", request.getRequestURI(), jReq);
    } catch (Exception ignore) {
      // ignore exception
    }
  }
}
