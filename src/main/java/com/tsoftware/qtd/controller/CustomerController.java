package com.tsoftware.qtd.controller;

import com.tsoftware.qtd.dto.ApiResponse;
import com.tsoftware.qtd.dto.credit.CreditRequest;
import com.tsoftware.qtd.dto.credit.CreditResponse;
import com.tsoftware.qtd.dto.customer.CustomerRequest;
import com.tsoftware.qtd.dto.customer.CustomerResponse;
import com.tsoftware.qtd.service.CreditService;
import com.tsoftware.qtd.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired private CustomerService customerService;
  @Autowired private CreditService creditService;

  @PostMapping
  public ResponseEntity<ApiResponse<CustomerResponse>> create(
      @RequestBody CustomerRequest customerRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Created", customerService.create(customerRequest)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerResponse>> update(
      @PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
    return ResponseEntity.ok(
        new ApiResponse<>(1000, "Updated", customerService.update(id, customerRequest)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    customerService.delete(id);
    return ResponseEntity.ok(new ApiResponse<>(1000, "Deleted", null));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable Long id) {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched", customerService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAll() {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Fetched All", customerService.getAll()));
  }

  @PostMapping("/{id}/credit")
  public ResponseEntity<ApiResponse<CreditResponse>> create(
      @RequestBody CreditRequest creditRequest, @PathVariable Long id) throws Exception {
    return ResponseEntity.ok(new ApiResponse<>(1000, "Created", creditService.create(id)));
  }
}
