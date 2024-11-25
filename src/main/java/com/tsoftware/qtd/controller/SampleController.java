package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.annotation.WorkflowAPI;
import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.model.OnboardingWorkflowRequest;
import com.tsoftware.qtd.model.OnboardingWorkflowResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @WorkflowAPI
  @PostMapping("/sample")
  public ResponseEntity<?> sample(@RequestBody OnboardingWorkflowRequest request) {

    OnboardingWorkflowResponse response = new OnboardingWorkflowResponse();
    response.setTargetId(request.getTargetId());
    response.setCurrentStep(request.getCurrentStep());
    response.setNextStep("nextStep");
    response.setIsSuccess(
        Boolean.valueOf(request.getMetadata().getOrDefault("isSuccess", false).toString()));
    return ResponseEntity.ok(
        ApiResponse.builder().code(1).message("Sample API").result(response).build());
  }
}
