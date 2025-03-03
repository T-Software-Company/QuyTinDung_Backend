package com.tsoftware.qtd.commonlib.helper;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

import com.tsoftware.qtd.commonlib.annotation.TargetId;
import com.tsoftware.qtd.commonlib.annotation.TransactionId;
import com.tsoftware.qtd.commonlib.exception.WorkflowException;
import com.tsoftware.qtd.commonlib.model.ApiResponse;
import com.tsoftware.qtd.commonlib.util.StringUtils;
import com.tsoftware.qtd.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class WorkflowAspectExtractor {
  public Map<String, Object> extractRequest(JoinPoint joinPoint) {
    Map<String, Object> argsMap = extractMethodArguments(joinPoint);
    enrichWithHttpRequest(argsMap);
    enrichWithUserContext(argsMap);
    return argsMap;
  }

  public UUID extractTargetId(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    Object[] args = joinPoint.getArgs();

    for (int i = 0; i < paramAnnotations.length; i++) {
      for (Annotation annotation : paramAnnotations[i]) {
        if (annotation instanceof TargetId) {
          if (args[i] instanceof UUID uuid) {
            return uuid;
          }
          if (args[i] instanceof String str) {
            return UUID.fromString(str);
          }
        }
      }
    }
    throw new WorkflowException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Target id not try");
  }

  public Map<String, Object> extractResponse(Object responseEntity) {
    var result = new HashMap<String, Object>();
    var response = ((ResponseEntity<?>) responseEntity).getBody();
    if (response instanceof ApiResponse<?> apiResponse) {
      response = apiResponse.getResult();
    }
    if (response != null) {
      String key;
      if (response instanceof Collection<?> collection && !collection.isEmpty()) {
        Object firstElement = collection.iterator().next();
        if (firstElement != null) {
          key = pluralize(firstElement.getClass().getSimpleName());
        } else {
          key = null;
        }
      } else {
        key = StringUtils.lowercaseFirstLetter(response.getClass().getSimpleName());
      }
      if (key != null) {
        result.put(key, response);
      }
    }

    return result;
  }

  public UUID extractTransactionId(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Annotation[][] paramAnnotations = method.getParameterAnnotations();
    Object[] args = joinPoint.getArgs();

    for (int i = 0; i < paramAnnotations.length; i++) {
      for (Annotation annotation : paramAnnotations[i]) {
        if (annotation instanceof TransactionId) {
          if (args[i] instanceof UUID uuid) {
            return uuid;
          }
          if (args[i] instanceof String str) {
            return UUID.fromString(str);
          }
        }
      }
    }
    throw new WorkflowException(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Can't try transaction id");
  }

  public String extractErrorMessage(Throwable ex) {
    if (ex instanceof CommonException commonException) {
      String message = commonException.getMessage();
      var params = commonException.getParameters().toArray();
      return String.format(message.replace("{}", "%s"), params);
    }
    return ex.getMessage();
  }

  private Map<String, Object> extractMethodArguments(JoinPoint joinPoint) {
    Map<String, Object> argsMap = new HashMap<>();
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    String[] argNames = codeSignature.getParameterNames();
    Object[] argValues = joinPoint.getArgs();

    for (int i = 0; i < argNames.length; i++) {
      Object argValue = argValues[i];
      String key;

      if (argValue == null
          || isPrimitiveOrWrapper(argValue.getClass())
          || argValue instanceof String) {
        key = argNames[i];
      } else if (argValue instanceof Collection<?> collection && !collection.isEmpty()) {
        Object firstElement = collection.iterator().next();
        key = firstElement != null ? pluralize(firstElement.getClass().getSimpleName()) : "items";
      } else if (argValue instanceof Map<?, ?> map && !map.isEmpty()) {
        key = argNames[i];
      } else {
        key = StringUtils.lowercaseFirstLetter(argValue.getClass().getSimpleName());
      }

      argsMap.put(key, argValue);
    }
    return argsMap;
  }

  private void enrichWithHttpRequest(Map<String, Object> argsMap) {
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest request = requestAttributes.getRequest();
      argsMap.put("endpoint", request.getRequestURI());
      argsMap.put("ip", request.getRemoteAddr());
    }
    argsMap.put("time", ZonedDateTime.now());
  }

  private void enrichWithUserContext(Map<String, Object> argsMap) {
    argsMap.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
  }

  private String pluralize(String word) {
    if (word.endsWith("y")) {
      return word.substring(0, word.length() - 1) + "ies";
    } else if (word.endsWith("s")
        || word.endsWith("x")
        || word.endsWith("z")
        || word.endsWith("sh")
        || word.endsWith("ch")) {
      return word + "es";
    } else {
      return word + "s";
    }
  }
}
