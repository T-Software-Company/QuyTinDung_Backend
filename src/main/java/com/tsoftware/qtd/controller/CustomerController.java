package com.tsoftware.qtd.controller;

import com.tsoftware.commonlib.annotation.WorkflowAPI;
import com.tsoftware.commonlib.model.ApiResponse;
import com.tsoftware.qtd.dto.credit.ApplicationRequest;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.service.ApplicationService;
import com.tsoftware.qtd.service.CustomerService;
import com.tsoftware.qtd.service.impl.DocumentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final ApplicationService applicationService;
  private final DocumentService documentService;

  @WorkflowAPI
  @PostMapping
  public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest customerRequest)
      throws Exception {
    return ResponseEntity.ok(customerService.create(customerRequest));
  }

  @WorkflowAPI
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerResponse>> update(
      @PathVariable UUID id, @RequestBody CustomerRequest customerRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(200, "Updated", customerService.update(id, customerRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
    customerService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(200, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(new ApiResponse<>(200, "Fetched", customerService.getById(id)));
  }

  @GetMapping("/{id}/documents")
  public ResponseEntity<?> getDocumentBelongToCustomer(@PathVariable UUID id) {
    return ResponseEntity.ok(documentService.getDocumentBelongToCustomer(id));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(200, "Fetched All", customerService.getAll()));
  }

  @PostMapping("/{id}/credit")
  public ResponseEntity<?> create(
      @RequestBody ApplicationRequest applicationRequest, @PathVariable UUID id) throws Exception {
    return ResponseEntity.ok(
        new ApiResponse<>(200, "Created", applicationService.create(applicationRequest)));
  }
}
