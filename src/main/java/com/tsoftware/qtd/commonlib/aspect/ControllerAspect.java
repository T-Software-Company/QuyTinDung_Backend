package com.tsoftware.qtd.commonlib.aspect;

import com.tsoftware.qtd.commonlib.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
@Slf4j
public class ControllerAspect {

  private static final String SUCCESS_MESSAGE = "Success";

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Around(
      value =
          "execution(* *..controller..*(..)) || execution(* org.springframework.web.bind.annotation.RestController.*(..))")
  protected Object proceedControllerResponse(ProceedingJoinPoint pjp) throws Throwable {
    return proceedResponse(pjp);
  }

  private Object proceedResponse(ProceedingJoinPoint pjp) throws Throwable {
    var response = pjp.proceed();
    if (response instanceof ResponseEntity<?> res) {
      if (res.getBody() instanceof ApiResponse<?> apiResponse) {
        return res;
      }
      ApiResponse<Object> responseData = new ApiResponse<>();
      responseData.setMessage(SUCCESS_MESSAGE);
      responseData.setCode(res.getStatusCode().value());
      responseData.setResult(res.getBody());
      return ResponseEntity.status(res.getStatusCode()).body(responseData);
    }
    return response;
  }
}
