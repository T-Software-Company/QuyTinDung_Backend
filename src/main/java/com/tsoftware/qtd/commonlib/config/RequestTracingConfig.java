package com.tsoftware.qtd.commonlib.config;

import static com.tsoftware.qtd.commonlib.constant.BaseConstant.TRACE_ID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class RequestTracingConfig {
  @Bean
  public OncePerRequestFilter traceIdFilter() {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain)
          throws ServletException, IOException {
        try {
          String traceId = UUID.randomUUID().toString();
          if (request.getHeader(TRACE_ID) != null) {
            traceId = request.getHeader(TRACE_ID);
          }
          request.setAttribute(TRACE_ID, traceId);
          MDC.put(TRACE_ID, traceId);
          response.addHeader(TRACE_ID, traceId);
          filterChain.doFilter(request, response);
        } finally {
          MDC.clear();
        }
      }
    };
  }
}
