package com.tsoftware.qtd.aspect;

import com.tsoftware.qtd.constants.EnumType.WorkflowStatus;
import com.tsoftware.qtd.context.WorkflowContext;
import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.model.OnboardingWorkflow;
import com.tsoftware.qtd.model.Workflow;
import com.tsoftware.qtd.model.WorkflowRequest;
import com.tsoftware.qtd.model.WorkflowResponse;
import com.tsoftware.qtd.service.impl.WorkflowService;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class WorkflowAspect {

  @Autowired private WorkflowService workflowService;

  @Autowired private BeanFactory beanFactory;

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Around("publicMethod() && @annotation(com.tsoftware.qtd.annotation.WorkflowAPI)")
  public Object proceedWorkflow(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    var optWorkflowRequest =
        Stream.of(proceedingJoinPoint.getArgs())
            .filter(arg -> arg instanceof WorkflowRequest)
            .findFirst();

    if (
    /*WorkflowContext.get() == null &&*/ optWorkflowRequest
        .isPresent()) { // if WorkflowContext existed, ignore the nested one.
      // retrieve workflow
      var workflowRequest = (WorkflowRequest) optWorkflowRequest.get();
      var workflow = getWorkflow(workflowRequest, proceedingJoinPoint);
      WorkflowContext.set(workflow);

      if (workflowRequest.getCurrentStep() != null) {
        // validate workflow
        workflowService.validateStep(workflowRequest);

        var isEmptyTarget = workflow.getTargetId() == null;
        if (isEmptyTarget) {
          workflow.setTargetId(UUID.randomUUID());
        }

        // proceed API
        Object rawResponse;
        WorkflowStatus recentStatus = workflow.getTargetId() == null ? null : workflow.getStatus();
        try {
          rawResponse = proceedingJoinPoint.proceed();
        } catch (Exception ex) {
          if (workflow.getTargetId() != null && workflow.getStatus() == WorkflowStatus.DENIED) {
            workflowService.save(workflow, recentStatus);
          }
          throw ex;
        } finally {
          WorkflowContext.clear();
        }

        // calculate next step and set to response
        var workflowResponse = getWorkflowResponse(rawResponse);
        if (workflowResponse != null) {
          if (workflowRequest.getCurrentStep() != null) {
            workflow.setNextStep(
                workflowService.calculateNextStep(workflowRequest, workflowResponse));
          }
          if (workflow.getStatus() == WorkflowStatus.INPROGRESS) {
            if (WorkflowStatus.COMPLETED.getShortName().equalsIgnoreCase(workflow.getNextStep())) {
              workflow.setStatus(WorkflowStatus.COMPLETED);
            }
            if (WorkflowStatus.DENIED.getShortName().equalsIgnoreCase(workflow.getNextStep())) {
              workflow.setStatus(WorkflowStatus.DENIED);
            }
            if (WorkflowStatus.EXPIRED.getShortName().equalsIgnoreCase(workflow.getNextStep())) {
              workflow.setStatus(WorkflowStatus.COMPLETED);
            }
          }

          if (isEmptyTarget && workflowResponse.getTargetId() != null) {
            workflow.setTargetId(workflowResponse.getTargetId());
          }

          if (workflow.getTargetId() != null || workflowRequest.getCurrentStep() != null) {
            workflowService.save(workflow, recentStatus);
          }

          if (workflowResponse.getTargetId() == null) {
            workflowResponse.setTargetId(workflow.getTargetId());
          }
          workflowResponse.setCurrentStep(workflow.getCurrentStep());
          workflowResponse.setNextStep(workflow.getNextStep());
          workflowResponse.setStatus(workflow.getStatus());
        }

        return rawResponse;
      }
    }

    try {
      return proceedingJoinPoint.proceed();
    } finally {
      WorkflowContext.clear();
    }
  }

  private Workflow getWorkflow(
      WorkflowRequest workflowRequest, ProceedingJoinPoint proceedingJoinPoint) {
    //    var signature = (MethodSignature) proceedingJoinPoint.getSignature();
    //    var method = signature.getMethod();
    //    var workflowAPI = method.getAnnotation(WorkflowAPI.class);

    var workflowTargetTargetUuid = workflowRequest.getTargetId();
    Workflow workflow = null;
    if (workflowTargetTargetUuid != null) {
      workflow = workflowService.get(workflowTargetTargetUuid);
    }
    if (workflow == null) {
      workflow = initWorkflow(workflowTargetTargetUuid);
    }
    if (workflowRequest.getCurrentStep() != null) {
      workflow.setCurrentStep(workflowRequest.getCurrentStep());
    }

    return workflow;
  }

  private Workflow initWorkflow(UUID targetTargetUuid) {
    Workflow workflow =
        OnboardingWorkflow.builder().targetId(targetTargetUuid).metadata(new HashMap<>()).build();
    workflow.setStatus(WorkflowStatus.INPROGRESS);
    workflow.setMetadata(new HashMap<>());
    return workflow;
  }

  private WorkflowResponse getWorkflowResponse(Object rawResponse) {
    if (rawResponse instanceof WorkflowResponse) {
      return (WorkflowResponse) rawResponse;
    }

    if (rawResponse instanceof ResponseEntity) {
      var rawBody = ((ResponseEntity<?>) rawResponse).getBody();
      if (rawBody instanceof WorkflowResponse) {
        return (WorkflowResponse) rawBody;
      } else if (rawBody instanceof ApiResponse<?> response) {
        if (response.getResult() instanceof WorkflowResponse) {
          return (WorkflowResponse) response.getResult();
        }
      }
    }

    return null;
  }
}
