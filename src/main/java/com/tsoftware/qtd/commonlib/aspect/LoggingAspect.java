package com.tsoftware.qtd.commonlib.aspect;

import com.tsoftware.qtd.commonlib.util.JsonParser;
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
  private long startTime;

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @AfterReturning(
      value = "@within(org.springframework.web.bind.annotation.ControllerAdvice)",
      returning = "objResponse")
  public void afterExceptionAdvise(JoinPoint joinPoint, Object objResponse) {
    log.info("Exception Advise: {}", joinPoint.getSignature());
    if (objResponse instanceof ResponseEntity<?> response) {
      long endTime = System.currentTimeMillis();
      long duration = endTime - startTime;
      logResponse(getRequest(), response.getBody(), duration);
    }
  }

  @Around("@within(org.springframework.web.bind.annotation.RestController)")
  public Object proceedLogForRequest(ProceedingJoinPoint pjp) throws Throwable {
    this.startTime = System.currentTimeMillis();
    log.info("Request: {}", pjp.getSignature());
    var request = getRequest();
    var args = pjp.getArgs();
    logRequest(request, args);
    var response = pjp.proceed();
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    logResponse(getRequest(), response, duration);

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
      log.info("Request({}) {}, args: {}", request.getMethod(), request.getRequestURI(), jReq);
    } catch (Exception ignore) {
      // ignore exception
    }
  }

  public void logResponse(HttpServletRequest request, Object body, long duration) {
    try {
      String jReq = JsonParser.toString(body);
      log.info("Response({} ms), {},  body: {}", duration, request.getRequestURI(), jReq);
    } catch (Exception ignore) {
      // ignore exception
    }
  }
}
